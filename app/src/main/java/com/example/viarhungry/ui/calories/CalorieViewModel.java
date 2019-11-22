package com.example.viarhungry.ui.calories;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;


public class CalorieViewModel extends AndroidViewModel {

    private CalorieRepository repository;
    private String calorie_total;


    public CalorieViewModel(Application app) {
        super(app);
        repository = CalorieRepository.getInstance(app);
        calorie_total="0";

    }



    public void addFood(String name,String cal){
        repository.add(new Food(name,cal));
    }

    public void addFood(Food Food){     //has redundant protection since it cannot receive empty string
        if(Food.getCalories().equals(""))
            Food.setCalories("0");
        repository.add(Food);
    }

    public void removeLastFood(){
        repository.remove();
    }

    public Food getFood(int index){
        return repository.get(index);
    }

    public String updateCalorieTotal(){
        repository.refresh();

        int tmpC=0;

        if(repository.size()!=0) {
            for (int i = 0; i < repository.size(); i++)
                tmpC += Integer.parseInt(repository.get(i).getCalories());
        }

        calorie_total=""+tmpC;

        return calorie_total;
    }

    public ArrayList<Food> getFoods(){
        return repository.getAllFood();
    }

    public void  setFoods(ArrayList<Food> foods){
        repository.set(foods);
    }

}