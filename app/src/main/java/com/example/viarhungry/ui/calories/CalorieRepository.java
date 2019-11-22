package com.example.viarhungry.ui.calories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalorieRepository {

    private FoodList foods;

    private static CalorieRepository instance;

    private FoodDao foodDao;


    private CalorieRepository(Application app){
        foodDatabase database = foodDatabase.getInstance(app);
        foodDao = database.foodDao();
        foods = FoodList.getInstance();

    }

    public static CalorieRepository getInstance(Application app){
        if(instance==null){
            instance = new CalorieRepository(app);
        }
        return instance;
    }
    public void refresh(){
        new GetTodayFoodAsync(foodDao).execute();
    }


    public ArrayList<Food> getAllFood(){

        refresh();
        return foods.getAllFood();
    }

    public ArrayList<Food> getFoodForAllDays(){

        new GetFoodAsync(foodDao).execute();
        return foods.getAllFood();
    }

    public void add(Food food){
        foods.insert(food);
        new InsertFoodAsync(foodDao).execute(food);
    }

    public void deleteAllFoods(){
        foods.deleteAllFood();
    }

    public void remove(){
        foods.removeTop();
    }

    public Food get(int index){

        return foods.get(index);
    }

    public int size() {
        return foods.size();
    }

    public void set(ArrayList<Food> foods) {
        this.foods.set(foods);
    }

    private static class InsertFoodAsync extends AsyncTask<Food,Void,Void> {
        private FoodDao foodDao;

        private InsertFoodAsync(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {

            Log.e("DATABASE","To insert:"+foods[0].getName()+" at Date:" + foods[0].getDate());
            foodDao.insert(foods[0]);

            return null;
        }
    }

    private static class GetFoodAsync extends AsyncTask<Void, Void, Void> {
        private FoodDao foodDao;
        private FoodList foodListInstance;
        private GetFoodAsync(FoodDao foodDao){
            this.foodDao = foodDao;
            foodListInstance = FoodList.getInstance();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            foodListInstance.set((ArrayList<Food>) foodDao.list());
            return null;
        }
    }

    private static class GetTodayFoodAsync extends AsyncTask<Food,Void,Void> {
        private FoodDao foodDao;
        private String date;
        private FoodList foodListInstance;

        private GetTodayFoodAsync(FoodDao foodDao){
            this.foodDao = foodDao;
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            foodListInstance = FoodList.getInstance();
        }

        @Override
        protected Void doInBackground(Food... foods) {

            foodListInstance.set((ArrayList<Food>) foodDao.getTodayFood(date));
            return null;
        }
    }


}
