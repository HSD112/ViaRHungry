package com.example.viarhungry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.viarhungry.ui.calories.CalorieFragment;
import com.example.viarhungry.ui.water.WaterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String cupsDrunk;
    private int bar;
    private ArrayList<CalorieFragment.food> foods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cupsDrunk = "0";
        bar = 0;
        foods = new ArrayList<>();

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

    public String getCupsDrunk() {
        return cupsDrunk;
    }

    public void setCupsDrunk(String text) {
        cupsDrunk = text;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int value) {
        bar = value;
    }

    public void setFoods(ArrayList<CalorieFragment.food> foods){
        this.foods=foods;
    }
    public ArrayList<CalorieFragment.food> getFoods(){
        return foods;
    }
}
