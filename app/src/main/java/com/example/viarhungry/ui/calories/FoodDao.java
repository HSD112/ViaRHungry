package com.example.viarhungry.ui.calories;

import java.util.ArrayList;

public class FoodDao {
    private ArrayList<Food> allFood;
    private static FoodDao instance;

    private FoodDao(){
        allFood = new ArrayList<Food>();
    }

    public static FoodDao getInstance(){
        if(instance == null){
            instance = new FoodDao();
        }

        return instance;
    }

    public ArrayList<Food> getAllFood(){
        return allFood;
    }

    public void insert (Food Food){

        allFood.add(Food);
    }

    public void deleteAllFood(){
        allFood = new ArrayList<>();
    }

    public void removeTop(){

       allFood.remove(allFood.size()-1);
    }

    Food get(int index){

       return allFood.get(index);
    }

    int size() {
        return allFood.size();
    }

    void set(ArrayList<Food> foods) {
        allFood = foods;
    }
}


