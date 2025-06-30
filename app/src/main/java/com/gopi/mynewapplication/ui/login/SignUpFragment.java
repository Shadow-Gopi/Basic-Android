package com.gopi.mynewapplication.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.gopi.mynewapplication.R;
import com.gopi.mynewapplication.data.database.AppDatabase;
import com.gopi.mynewapplication.data.database.entity.UserEntity;
import com.gopi.mynewapplication.databinding.FragmentSignUpBinding;

import java.util.Objects;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;
    private LoginViewModel viewModel;
    private Handler handler = new Handler(Looper.myLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnRegister.setOnClickListener(v -> {
            String userName = Objects.requireNonNull(binding.etUsername.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
            if(!userName.isEmpty() && password.length() > 6){
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    boolean isUserAlreadyExist = viewModel.getIsUserExists(userName);
                    handler.post(() -> {
                        if(!isUserAlreadyExist){
                            UserEntity userEntity = new UserEntity(userName, password);
                            viewModel.insertUser(userEntity);
                            Navigation.findNavController(binding.getRoot())
                                    .popBackStack(R.id.signInFragment,false);
                        }else{
                            Toast.makeText(requireContext(), "User Is Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } else {
                Toast.makeText(requireContext(), "UserName must be not empty and password must be atleast 6 characters", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
