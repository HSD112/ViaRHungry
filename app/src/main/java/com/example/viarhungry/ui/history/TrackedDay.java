package com.example.viarhungry.ui.history;


public class TrackedDay {
    private int cupsOfWater;
    private int totalCalories;
    private String date;

    TrackedDay(int cups,int cal) {
        cupsOfWater=cups;
        totalCalories=cal;
    }

    public int getCupsOfWater() {
        return cupsOfWater;
    }

    public void setCupsOfWater(int cupsOfWater) {
        this.cupsOfWater = cupsOfWater;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }
}