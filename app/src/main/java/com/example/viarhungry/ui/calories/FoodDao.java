package com.example.viarhungry.ui.calories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("DELETE FROM food_table")
    void deleteAllNotes();

    @Query("SELECT * FROM food_table ORDER BY id DESC")
    List<Food> list();

    @Query("SELECT * FROM food_table WHERE date == :date")
    List<Food> getTodayFood(String date);

}
