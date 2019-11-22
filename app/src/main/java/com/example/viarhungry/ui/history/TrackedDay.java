package com.example.viarhungry.ui.history;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "day_table")
public class TrackedDay {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String calories;
    private String waterCups;
    private String date;

    public TrackedDay(String waterCups, String calories) {
        this.waterCups = waterCups;
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

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWaterCups() {
        return waterCups;
    }

    public void setWaterCups(String waterCups) {
        this.waterCups = waterCups;
    }
}
