package com.ivrndev.poultry_iot.ui.customize;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomizeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CustomizeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is customize fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}