package com.example.viarhungry.ui.calories;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "food_table")
public class Food {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String calories;
    private String date;

    public Food(String name, String calories) {
        this.name=name;
        this.calories=calories;
        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
    }

//    public Food(String name, String cal) {
//
//        this.name = name;
//        this.calories = cal;
//        this.date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//    }

//    public Food(String name, String cal, String date){
//        this.name=name;
//        calories=cal;
//        this.date=date;
//        this.id=id;
//    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
