package com.gopi.mynewapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gopi.mynewapplication.R;
import com.gopi.mynewapplication.databinding.ActivityLoginBinding;
import com.gopi.mynewapplication.ui.home.HomeActivity;
import com.gopi.mynewapplication.ui.todo.TodoActivity;
import com.gopi.mynewapplication.util.SharedPrefHelper;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private NavController navController;
    private SharedPrefHelper sharedPrefHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check if user is already logged in
        SharedPrefHelper sharedPrefHelper = SharedPrefHelper.getInstance(this);
        if (!Objects.equals(sharedPrefHelper.getUserName(), "")) {
            startActivity(new Intent(this, TodoActivity.class));
            finish();
            return;
        }

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHosFragment);
        navController = navHostFragment.getNavController();
        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.login_nav_graph);
        navController.setGraph(navGraph);
        setSupportActionBar(binding.mtbHome);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.signInFragment
        ).build();
        NavigationUI.setupWithNavController(binding.mtbHome, navController, appBarConfiguration);

    }
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}