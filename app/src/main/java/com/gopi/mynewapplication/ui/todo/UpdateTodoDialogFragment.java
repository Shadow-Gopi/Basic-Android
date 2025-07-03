package com.gopi.mynewapplication.ui.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.gopi.mynewapplication.R;
import com.gopi.mynewapplication.data.database.entity.TodoEntity;
import com.gopi.mynewapplication.databinding.DialogEditTodoBinding;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateTodoDialogFragment extends DialogFragment {

    private TodoViewModel viewModel;

    private DialogEditTodoBinding mBinding;
    String title, detail;
    int id;
    long date;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mBinding = DialogEditTodoBinding.inflate(getLayoutInflater(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        Bundle args = getArguments();
        if (args != null){
            id = args.getInt("id",0);
            title = args.getString("title");
            detail = args.getString("detail");
            date = args.getLong("date");
        }

        mBinding.etTitli.setText(title);
        mBinding.etDetail.setText(detail);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(date * 1000);

        mBinding.etDate.setText(formattedDate);

        final long[] epoch = {0};
        mBinding.etDate.setOnClickListener(v -> {
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

                            mBinding.etDate.setText(formattedDate);
                        }
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        mBinding.btnSave.setOnClickListener(v -> {
            String title = mBinding.etTitli.getText().toString().trim();
            String detail = mBinding.etDetail.getText().toString().trim();
            String dateStr = mBinding.etDate.getText().toString().trim();

            Log.e("Dialog", "title: "+ title + " detial: " + detail + " date: " + epoch[0]);

            if (title.isEmpty() || detail.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.updateTodo(id,title,detail,epoch[0]);

            dismiss();
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

}
