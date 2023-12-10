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


    ActivityMainBinding binding;// Binding object for the main activity layout


    ActionBarDrawerToggle mToggle;
    DBHelper dbHelper;
    TextView displayUsername;
    FrameLayout fragmentContainer;
    SharedPreferences sharedPreferences;

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

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        displayUsername = binding.edUsername;
        fragmentContainer = binding.frame;


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String storedUserName = preferences.getString("userName", null);

        displayUsername.setText(storedUserName);


    }

    private void init(){

        mToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.materialToolbar,R.string.nav_open,R.string.nav_close);
        binding.drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        mToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));


        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetNavigationDrawer();

    }

    private void SetNavigationDrawer() {

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Fragment selectedFragment = null;

                int itemId = item.getItemId();



                if (itemId == R.id.nav_add_stock ){
                    selectedFragment = new AddStockFragment();
                } else if (itemId == R.id.nav_sales ){
                    selectedFragment = new SalesFragment();
                }else if (itemId == R.id.nav_purchase ){

                }else if (itemId == R.id.nav_search_stock ){

                }else if (itemId == R.id.nav_list_stock ){

                }else if (itemId == R.id.nav_log_out ){


                    SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();


                    Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                    startActivity(intent);

                }


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