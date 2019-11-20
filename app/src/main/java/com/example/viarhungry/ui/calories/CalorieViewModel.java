package com.example.viarhungry.ui.calories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalorieViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CalorieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is calorie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}