package com.gopi.mynewapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.gopi.mynewapplication.databinding.FragmentSignInBinding;
import com.gopi.mynewapplication.ui.home.HomeActivity;
import com.gopi.mynewapplication.util.SharedPrefHelper;

import java.util.Objects;

public class SignInFragment extends Fragment {
    FragmentSignInBinding binding;
    private LoginViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignInBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        //Register button
        binding.tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signInFragment_to_signUpFragment);
        });

        Handler handler = new Handler();

        binding.btnLogin.setOnClickListener(v -> {
            String userName = Objects.requireNonNull(binding.etUsername.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.etPassword.getText()).toString();


            if (!userName.isEmpty() && !password.isEmpty()){
                AppDatabase.databaseWriteExecutor.execute(() -> {
                    String storedPassword = viewModel.getPassword(userName);
                    handler.post(() -> {

                        // passwor check

                        if(password.equals(storedPassword)){
                            Intent intent = new Intent(requireContext(), HomeActivity.class);
                            SharedPrefHelper.getInstance(requireContext()).setUserName(userName);
                            startActivity(new Intent(requireContext(), HomeActivity.class));
                            requireActivity().finish();
                            Toast.makeText(requireContext(), "Login Sucessfull", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(requireContext(), "Password is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }else {
                Toast.makeText(requireContext(), "UserName and Password Must Not Be Empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
