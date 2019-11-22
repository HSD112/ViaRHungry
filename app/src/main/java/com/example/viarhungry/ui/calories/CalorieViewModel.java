package com.example.viarhungry.ui.calories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;


public class CalorieViewModel extends AndroidViewModel {

    private CalorieRepository repository;


    public CalorieViewModel(Application app) {
        super(app);
        repository = CalorieRepository.getInstance(app);

    }

    public void refreshView(){
        repository.refresh();
    }



    boolean addFood(String name, String cal){
        return repository.add(name,cal);
    }

    void removeLastFood(){
        repository.remove();
    }

    Food getFood(int index){
        return repository.get(index);
    }

    String updateCalorieTotal(){
        return repository.updateCalorieTotal();
    }

    ArrayList<Food> getFoods(){
        return repository.getAllFood();
    }

    void  setFoods(ArrayList<Food> foods){
        repository.set(foods);
    }

    int getFoodSize(){
    return repository.size();
    }

}