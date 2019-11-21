package com.example.viarhungry.ui.calories;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Food.class}, version = 1)
public abstract class foodDatabase extends RoomDatabase {

    private static foodDatabase instance;
    public abstract FoodDao foodDao();

    public static synchronized foodDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    foodDatabase.class, "food_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
