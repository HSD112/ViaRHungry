package com.example.viarhungry.ui.calories;

import java.util.ArrayList;

public class CalorieRepository {

    private FoodDao foods;

    private static CalorieRepository instance;

    private CalorieRepository(){
        foods = FoodDao.getInstance();
    }

    public static CalorieRepository getInstance(){
        if(instance==null){
            instance = new CalorieRepository();
        }
        return instance;
    }

    public ArrayList<Food> getAllFood(){
        return foods.getAllFood();
    }

    public void add(Food food){
        foods.insert(food);
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
}
