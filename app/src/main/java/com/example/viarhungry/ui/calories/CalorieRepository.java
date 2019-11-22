package com.example.viarhungry.ui.calories;

import android.app.Application;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalorieRepository {

    private FoodList foods;

    private static CalorieRepository instance;

    private FoodDao foodDao;


    private CalorieRepository(Application app){
        FoodDatabase database = FoodDatabase.getInstance(app);
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

    public String updateCalorieTotal(){
        int tmpC=0;

        if(size()!=0) {
            for (int i = 0; i < size(); i++)
                tmpC += Integer.parseInt(get(i).getCalories());
        }
        return ""+tmpC;
    }


    public boolean add(String name,String cal){
        if(name.equals("")) name = "Food";


        if(!cal.equals("")) {

            Food newFood = new Food(name, cal);
            foods.insert(newFood);
            new InsertFoodAsync(foodDao).execute(newFood);
            //no need to use refresh here since it should be the same
            return true;
        }
        return false;
    }

    public void deleteAllFoods(){
        foods.deleteAllFood();
    }

    public void remove(){
        new DeleteFoodAsync(foodDao).execute(foods.get(foods.size()-1)); //deletes from db
        foods.removeTop(); // deletes from memory
    }

    public Food get(int index){
        refresh();
        return foods.get(index);
    }

    public int size() {
        refresh();
        if(foods!= null)
            return foods.size();

        foods = FoodList.getInstance();
        return foods.size();
    }

    public void set(ArrayList<Food> foods) {
        refresh();
        this.foods.set(foods);
    }

    private static class InsertFoodAsync extends AsyncTask<Food,Void,Void> {
        private FoodDao foodDao;

        private InsertFoodAsync(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
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

            foodListInstance.set((ArrayList<Food>) foodDao.getAllFoodsFromDb(date));
            return null;
        }
    }

    private static class DeleteFoodAsync extends AsyncTask<Food,Void,Void> {
        private FoodDao foodDao;

        private DeleteFoodAsync(FoodDao foodDao){
            this.foodDao = foodDao;
        }

        @Override
        protected Void doInBackground(Food... foods) {
            foodDao.delete(foods[0]);
            return null;
        }
    }


}
