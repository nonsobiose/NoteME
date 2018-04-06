package com.nonsobiose.noteme;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by Chidiebere on 3/22/2018.
 */

public class Factory extends ViewModelProvider.NewInstanceFactory {

    private Application mApplication;
    private int mParam;


    public Factory(Application application, int param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new EditNoteViewModel(mApplication, mParam);
    }
}
