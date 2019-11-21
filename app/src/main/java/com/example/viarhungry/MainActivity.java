package com.example.viarhungry;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.viarhungry.ui.calories.CalorieFragment;
import com.example.viarhungry.ui.calories.food;
import com.example.viarhungry.ui.water.WaterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String cupsDrunk;
    private int bar;
    private ArrayList<food> foods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cupsDrunk = "0";
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

    public int getBar() {
        return bar;
    }

    public void setBar(int value) {
        bar = value;
    }

    public void setFoods(ArrayList<food> foods){
        this.foods=foods;
    }
    public ArrayList<food> getFoods(){
        return foods;
    }
    public void saveLocalData(){


    }
    public void loadLocalData(){

    }
}
