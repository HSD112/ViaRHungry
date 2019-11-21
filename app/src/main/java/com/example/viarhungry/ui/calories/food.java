package com.example.viarhungry.ui.calories;

public class food {
    private String name;
    private String calories;

    food(String name, String cal) {
        this.name = name;
        calories = cal;
    }


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
}
