package com.gopi.mynewapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gopi.mynewapplication.R;
import com.gopi.mynewapplication.data.database.entity.UserEntity;
import com.gopi.mynewapplication.databinding.FragmentUserBinding;
import com.gopi.mynewapplication.ui.login.LoginActivity;
import com.gopi.mynewapplication.ui.login.LoginViewModel;
import com.gopi.mynewapplication.util.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;

    private UserViewModel viewModel;

    SharedPrefHelper sharedPrefHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentUserBinding .inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getUsers().observe(getViewLifecycleOwner(),this::handelUsersFromDB);
    }

    private void handelUsersFromDB(List<UserEntity> userEntities) {

        Log.e("UserFrag","size: " + userEntities.size());

            List<String> userInfoList = new ArrayList<>();
            for (UserEntity user : userEntities) {
                String info = "Username: " + user.getUserName() + "\nPassword: " + user.getPassword();
                userInfoList.add(info);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    requireContext(), android.R.layout.simple_list_item_1, userInfoList);

            binding.userList.setAdapter(adapter);


    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.menu_item_logout) {
//            SharedPrefHelper sharedPrefHelper = SharedPrefHelper.getInstance(requireContext());
//            sharedPrefHelper.clearPref();
//            startActivity(new Intent(requireContext(), LoginActivity.class));
//            requireActivity().finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
