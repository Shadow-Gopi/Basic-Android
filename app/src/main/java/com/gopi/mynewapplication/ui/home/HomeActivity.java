package com.gopi.mynewapplication.ui.home;

import static com.gopi.mynewapplication.util.SharedPrefHelper.sharedPrefHelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if (item.getItemId() == R.id.menu_item_logout) {
             new AlertDialog.Builder(this)
                     .setTitle("Sign Out")
                             .setMessage("Are you sure you wanna logout?")
                                     .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int which) {
                                             sharedPrefHelper.clearPref();
                                             Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                             startActivity(intent);
                                             finish();
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
        return super.onOptionsItemSelected(item);
    }
}
