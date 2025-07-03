package com.gopi.mynewapplication.ui.todo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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
import com.gopi.mynewapplication.databinding.ActivityTodoBinding;
import com.gopi.mynewapplication.ui.home.HomeActivity;
import com.gopi.mynewapplication.ui.login.LoginActivity;
import com.gopi.mynewapplication.util.SharedPrefHelper;

public class TodoActivity extends AppCompatActivity {
    private NavController navController;
    ActivityTodoBinding binding;
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefHelper = SharedPrefHelper.getInstance(this);

        final NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.navHosFragment);
        navController = navHostFragment.getNavController();

        NavInflater navInflater = navController.getNavInflater();
        NavGraph navGraph = navInflater.inflate(R.navigation.todo_nav_graph);
        navController.setGraph(navGraph);

        setSupportActionBar(binding.mtbHome);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.todoFragment
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
        MenuItem menuItem = menu.findItem(R.id.menu_item_logout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            menuItem.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }
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
                            Intent intent = new Intent(TodoActivity.this, LoginActivity.class);
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



/*

1. Todo create fragment
2. TextBox -> 1. title 2. todoDetails
3. Calender -> select and store date in epoch format
4. Button -> Save
5. OnClick of save -> insert title,details,date(long)

1. Todo Read fragment
2. get all todos from table
3. display in recyclerview


 */