package com.example.viarhungry;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.viarhungry.ui.calories.Food;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String cupsDrunk;
    private ArrayList<Food> foods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cupsDrunk = "0"; // this can be saved with shared preference because it's a simple String
        foods = new ArrayList<>(); // need DAO for this

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_water, R.id.navigation_calories, R.id.navigation_history)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("DailyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cupsDrunk", cupsDrunk);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("DailyData", MODE_PRIVATE);
        cupsDrunk= prefs.getString("cupsDrunk", "0");
    }



    public String getCupsDrunk() {
        return cupsDrunk;
    }

    public void setCupsDrunk(String text) {
        cupsDrunk = text;
    }

    public void setFoods(ArrayList<Food> foods){
        this.foods=foods;
    }
    public ArrayList<Food> getFoods(){
        return foods;
    }
}
