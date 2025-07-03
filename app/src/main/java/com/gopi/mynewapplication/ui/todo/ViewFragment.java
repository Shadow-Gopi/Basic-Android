package com.gopi.mynewapplication.ui.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gopi.mynewapplication.data.database.entity.TodoEntity;
import com.gopi.mynewapplication.databinding.FragmentViewBinding;
import com.gopi.mynewapplication.ui.home.HomeActivity;
import com.gopi.mynewapplication.ui.login.LoginActivity;

import java.util.List;

public class ViewFragment extends Fragment {

    FragmentViewBinding binding;
    private TodoAdapter adapter;

    private TodoViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        viewModel.getAllTodos().observe(getViewLifecycleOwner(),this::handleAllTodosDB);
    }


// recycllier view and adapter setup
    private void handleAllTodosDB(List<TodoEntity> todoEntities) {
        if (todoEntities != null && !todoEntities.isEmpty()){
            binding.rvTodos.setLayoutManager(new LinearLayoutManager(requireContext()));
            adapter = new TodoAdapter(new TodoAdapter.OnClickListener() {
                @Override
                public void onEditClick(TodoEntity item) {
                    UpdateTodoDialogFragment dialogFragment = new UpdateTodoDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", item.getId());
                    bundle.putString("title", item.getTitle());
                    bundle.putString("detail", item.getDetail());
                    bundle.putLong("date", item.getDate());
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getChildFragmentManager(), "Todo_Dialog_Fragment");
                }

                @Override
                public void onDeleteClick(int id) {
                    new AlertDialog.Builder(requireContext())
                            .setTitle("Delete")
                            .setMessage("Are you sure you wanna Delete?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    viewModel.deleteTodo(id);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                }
            });
            binding.rvTodos.setAdapter(adapter);
            adapter.submitList(todoEntities);
        }
    }
}
