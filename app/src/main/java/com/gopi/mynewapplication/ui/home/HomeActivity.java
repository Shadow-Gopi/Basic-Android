package com.gopi.mynewapplication.ui.home;

import static com.gopi.mynewapplication.util.SharedPrefHelper.sharedPrefHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.gopi.mynewapplication.R;
import com.gopi.mynewapplication.databinding.ActivityHomeBinding;
import com.gopi.mynewapplication.databinding.ActivityLoginBinding;
import com.gopi.mynewapplication.ui.login.LoginActivity;
import com.gopi.mynewapplication.util.SharedPrefHelper;

public class HomeActivity extends AppCompatActivity {

    private NavController navController;
    ActivityHomeBinding binding;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefHelper = SharedPrefHelper.getInstance(this);

        final NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.navHosFragment);
        navController = navHostFragment.getNavController();

        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.home_nav_graph);
        navController.setGraph(navGraph);

        setSupportActionBar(binding.mtbHome);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.userFragment
        ).build();

        NavigationUI.setupWithNavController(binding.mtbHome, navController, appBarConfiguration);

        // ✅ Set logout button listener
        binding.customLogoutButton.setOnClickListener(v -> {
            sharedPrefHelper.clearPref();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item); // No menu items used
    }
}
