package com.gopi.mynewapplication.ui.todo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gopi.mynewapplication.R;
import com.gopi.mynewapplication.data.database.entity.TodoEntity;

import com.gopi.mynewapplication.databinding.FragmentTodoBinding;

import com.gopi.mynewapplication.util.SharedPrefHelper;


import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodoFragment extends Fragment {
    FragmentTodoBinding binding;
    private TodoViewModel viewModel;
    private SharedPrefHelper sharedPrefHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        final long[] epoch = {0};
        binding.etDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) {
                            calendar.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);
                            epoch[0] = calendar.getTimeInMillis() / 1000;
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String formattedDate = sdf.format(calendar.getTime());

                            binding.etDate.setText(formattedDate);
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        binding.btnSave.setOnClickListener(v -> {
            String title = binding.etTitli.getText().toString().trim();
            String detail = binding.etDetail.getText().toString().trim();
            String dateStr = binding.etDate.getText().toString().trim();

            if (title.isEmpty() || detail.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            TodoEntity todo = new TodoEntity(title, detail, epoch[0]);

            viewModel.insertTodo(todo);
        });

        binding.btnAdd.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_todoFragment_to_viewFragment);
        });

        binding.btnSpeech.setOnClickListener(v ->{
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_todoFragment_to_speechFragment);
        });


    }



    // 1. Button - viewTodo
    // 2. ViewTodoFragment - recyclerview, adapter, card(title, details, date)
}
