package com.example.viarhungry.ui.history;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TrackedDay.class}, version = 1)
public abstract class TrackedDayDatabase extends RoomDatabase {

    private static TrackedDayDatabase instance;
    public abstract TrackedDayDao trackedDayDao();

    public static synchronized TrackedDayDatabase getInstance(Context context){
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TrackedDayDatabase.class, "day_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
