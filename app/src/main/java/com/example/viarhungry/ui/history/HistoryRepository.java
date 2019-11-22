package com.example.viarhungry.ui.history;

import android.app.Application;
import android.os.AsyncTask;

import com.example.viarhungry.ui.calories.Food;
import com.example.viarhungry.ui.calories.FoodDao;
import com.example.viarhungry.ui.calories.FoodList;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HistoryRepository {
    private TrackedDayList list;

    private static HistoryRepository instance;

    private TrackedDayDao dayDao;

    private HistoryRepository(Application app){
        TrackedDayDatabase database = TrackedDayDatabase.getInstance(app);
        dayDao = database.trackedDayDao();
        list = TrackedDayList.getInstance();
    }

    public static HistoryRepository getInstance(Application app){

        if(instance==null){
            instance = new HistoryRepository(app);
        }
        return instance;
    }

    public void refresh() {
        new GetDayListAsync(dayDao).execute();
    }

    public void setList(ArrayList<TrackedDay> newList) {
        list.set(newList);
    }

    public TrackedDay getDay(int position) {
        refresh();
        return list.get(position);
    }

    public int getSize() {
        refresh();
        return list.size();
    }

    public void addToday(ArrayList<Food> list,String cups){
        int calorieCounter=0;

        for(int i=0;i<list.size();i++) {
            calorieCounter += Integer.parseInt(
                    list.get(i).getCalories());
        }


        TrackedDay tempDay = new TrackedDay(cups,""+calorieCounter);
        new InsertDayAsync(dayDao).execute(tempDay);
    }


    private static class GetDayListAsync extends AsyncTask<Void, Void, Void> {
        private TrackedDayDao dayDao;
        private TrackedDayList tempList;
        private GetDayListAsync(TrackedDayDao dayDao){
            this.dayDao = dayDao;
            tempList = TrackedDayList.getInstance();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tempList.set((ArrayList<TrackedDay>) dayDao.list());
            return null;
        }
    }

    private static class InsertDayAsync extends AsyncTask<TrackedDay,Void,Void> {
        private TrackedDayDao dayDao;

        private InsertDayAsync(TrackedDayDao dayDao){
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(TrackedDay... days) {
            dayDao.insert(days[0]);

            return null;
        }
    }


}
