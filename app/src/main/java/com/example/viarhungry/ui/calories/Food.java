package com.example.viarhungry.ui.calories;

public class Food {
    private String name;
    private String calories;

    Food(String name, String cal) {
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
