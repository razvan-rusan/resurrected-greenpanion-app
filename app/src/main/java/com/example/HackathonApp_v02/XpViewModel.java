package com.example.HackathonApp_v02;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class XpViewModel extends ViewModel {

    private MutableLiveData<Number> xp = new MutableLiveData<Number>();

    public void setXp(Number xp) {
        this.xp.setValue(xp);
    }

    public LiveData<Number> getXp() {
        return this.xp;
    }

}
