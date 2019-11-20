package com.example.viarhungry.ui.water;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WaterViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WaterViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is water fragment");
    }
}