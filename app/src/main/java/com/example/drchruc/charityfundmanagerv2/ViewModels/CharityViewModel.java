package com.example.drchruc.charityfundmanagerv2.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.drchruc.charityfundmanagerv2.Repositories.CharityRepository;
import com.example.drchruc.charityfundmanagerv2.Entities.Charity;

import java.util.List;

public class CharityViewModel extends AndroidViewModel {
    private final CharityRepository mRepository;
    private LiveData<List<Charity>> mAllCharities;


    public CharityViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharityRepository(application);
        mAllCharities = mRepository.getAllCharities();
    }

    public LiveData<List<Charity>> getAllCharities() {
        return mAllCharities;
    }

    public void insert(Charity charity) {
        mRepository.insert(charity);
    }

    public void update(Charity charity) {
        mRepository.update(charity);
    }

    public void delete(Charity charity){
        mRepository.delete(charity);
    }


}
