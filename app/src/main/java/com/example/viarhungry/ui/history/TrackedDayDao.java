package com.example.viarhungry.ui.history;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;


import com.example.viarhungry.ui.calories.Food;

import java.util.List;

@Dao
public interface TrackedDayDao{


    @Insert
    void insert(TrackedDay day);

    @Update
    void update(TrackedDay day);

    @Delete
    void delete(TrackedDay day);

    @Query("DELETE FROM day_table")
    void deleteAllNotes();

    @Query("SELECT * FROM day_table ORDER BY id ASC")
    List<TrackedDay> list();
}
