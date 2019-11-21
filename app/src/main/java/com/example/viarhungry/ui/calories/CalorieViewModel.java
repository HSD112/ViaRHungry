package com.example.viarhungry.ui.calories;

import android.view.ViewParent;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.viarhungry.MainActivity;
import com.example.viarhungry.R;

import java.util.ArrayList;


public class CalorieViewModel extends ViewModel {

    private ArrayList<food> foods;

    private String calorie_total;


    public CalorieViewModel() {
        foods= new ArrayList<>();
        calorie_total="0";

    }



    public void addFood(String name,String cal){
        foods.add(new food(name,cal));
    }

    public void addFood(food Food){     //has redundant protection since it cannot receive empty string
        if(Food.getCalories().equals(""))
            Food.setCalories("0");
        foods.add(Food);
    }

    public void removeLastFood(){
        foods.remove(foods.size()-1);
    }

    public food getFood(int index){
        return foods.get(index);
    }

    public String updateCalorieTotal(){
        int tmpC=0;

        if(foods.size()!=0) {
            for (int i = 0; i < foods.size(); i++)
                tmpC += Integer.parseInt(foods.get(i).getCalories());
        }

        calorie_total=""+tmpC;

        return calorie_total;
    }

    public ArrayList<food> getFoods(){
        return foods;
    }

    public void  setFoods(ArrayList<food> foods){
        this.foods=foods;
    }

}