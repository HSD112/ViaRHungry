package com.example.viarhungry.ui.calories;

import java.util.ArrayList;

public class FoodList {
    private ArrayList<Food> allFood;
    private static FoodList instance;

    private FoodList(){
        allFood = new ArrayList<>();
    }

    public static FoodList getInstance(){
        if(instance == null){
            instance = new FoodList();
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


