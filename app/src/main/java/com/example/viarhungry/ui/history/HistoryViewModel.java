package com.example.viarhungry.ui.history;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.viarhungry.ui.calories.CalorieRepository;
import com.example.viarhungry.ui.calories.Food;

import java.util.ArrayList;

public class HistoryViewModel extends AndroidViewModel {

    private HistoryRepository repository;

    public HistoryViewModel(Application app) {
        super(app);
        repository = HistoryRepository.getInstance(app);
    }

    public void refreshView(){
        repository.refresh();
    }

    public void setList(ArrayList<TrackedDay> newList) {
        repository.setList(newList);
    }

    public TrackedDay getDay(int position) {
        return repository.getDay(position);
    }

    public int getSize() {
        return repository.getSize();
    }

    public void addToday(ArrayList<Food> list,String cups){
        repository.addToday(list,cups);
    }

}