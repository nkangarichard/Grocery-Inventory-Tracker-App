package com.example.rngrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.rngrocery.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding; // Binding object for the main activity layout

    ActionBarDrawerToggle mToggle;
    DBHelper dbHelper;
    TextView displayUsername;
    FrameLayout fragmentContainer;
    SharedPreferences sharedPreferences;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle toggle button clicks for the navigation drawer
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // Inflating the activity layout
        View view = binding.getRoot();
        setContentView(view);
        init(); // Initialize components

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        displayUsername = binding.edUsername;
        fragmentContainer = binding.frame;

        // Retrieve stored username from SharedPreferences and display it
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedUserName = preferences.getString("userName", null);
        displayUsername.setText(storedUserName);
    }

    private void init() {
        // Initialize the navigation drawer toggle and set up the toolbar
        mToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.materialToolbar, R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer
        SetNavigationDrawer();
    }

    private void SetNavigationDrawer() {
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item selection
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.nav_add_stock) {
                    selectedFragment = new AddStockFragment();
                } else if (itemId == R.id.nav_sales) {
                    selectedFragment = new SalesFragment();
                } else if (itemId == R.id.nav_purchase) {
                    selectedFragment = new PurchaseFragment();
                } else if (itemId == R.id.nav_search_stock) {
                  selectedFragment = new SearchFragment();
                } else if (itemId == R.id.nav_list_stock) {
                    selectedFragment = new ListStockFragment();
                } else if (itemId == R.id.nav_log_out) {
                    // Clear SharedPreferences and navigate to the login screen
                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);
                }

                // Replace the fragment in the container if a fragment is selected
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, selectedFragment)
                            .commit();
                }

                return false;
            }
        });
    }
}
