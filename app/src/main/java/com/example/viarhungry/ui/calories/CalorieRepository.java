package com.example.viarhungry.ui.calories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public ArrayList<Food> getAllFood(){
        ArrayList<Food> newList = new ArrayList<>();
                new GetFoodAsync(null,foodDao,newList);
        if(newList != null)
             Log.e("DATABASE READ",newList.get(0).getName());
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

    private static class GetFoodAsync extends AsyncTask<Food, Void, List<Food>> {
        private FoodDao foodDao;

        private GetFoodAsync(FoodDao dao, FoodDao foodDao, ArrayList<Food> newList){
            this.foodDao = foodDao;
        }

        @Override
        protected ArrayList<Food> doInBackground(Food... foods) {

            if(foodDao.list() != null)
                return (ArrayList<Food>) foodDao.list();
            else return new ArrayList<>();
        }
    }
}
