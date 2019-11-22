package com.example.viarhungry.ui.history;

import java.util.ArrayList;

public class TrackedDayList {
    private ArrayList<TrackedDay> allDays;
    private static TrackedDayList instance;

    private TrackedDayList(){
        allDays = new ArrayList<>();
    }

    public static TrackedDayList getInstance(){
        if(instance == null){
            instance = new TrackedDayList();
        }

        return instance;
    }

    public ArrayList<TrackedDay> getAllFood(){
        return allDays;
    }

    public void insert (TrackedDay day){

        allDays.add(day);
    }

    public void deleteAllFood(){
        allDays = new ArrayList<>();
    }


    TrackedDay get(int index){

        return allDays.get(index);
    }

    int size() {
        return allDays.size();
    }

    void set(ArrayList<TrackedDay> day) {
        allDays = day;
    }
}


