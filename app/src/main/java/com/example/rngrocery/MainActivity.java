package com.example.rngrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.rngrocery.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;// Binding object for the main activity layout


    ActionBarDrawerToggle mToggle;

    TextView displayUsername;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());// Inflating the  activity layout
        View view = binding.getRoot();
        setContentView(view);
        init();


        displayUsername = binding.dUsername;





        // Retrieve data passed from the previous activity (MainActivity)
        String receivedUserName = getIntent().getStringExtra("userName");


        // Set the text of TextViews with received data
        displayUsername.setText(receivedUserName);

    }

    private void init(){

        mToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.materialToolbar,R.string.nav_open,R.string.nav_close);
        binding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetNavigationDrawer();

    }

    private void SetNavigationDrawer() {

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }


}