package com.abstractclass.visitormanager.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.abstractclass.visitormanager.controller.MRTD;
import com.abstractclass.visitormanager.models.Person;

public class MRZViewModel extends ViewModel {
    private MRTD mrtd = new MRTD();
    private MutableLiveData<String> textblock = new MutableLiveData<>();
    /**
    private LiveData<String> mrzTextBlock = Transformations.switchMap(textblock, textblock -> {
        if(mrtd.isValidTD1(textblock)) {
            return mrtd.getMrtdText();
        }
        return mrtd.resetMrtdText();
    });
     */

    private LiveData<Person> person = Transformations.switchMap(textblock, textblock -> {
        if(mrtd.isValidTD1(textblock)) {
            return mrtd.getPerson();
        }
        return mrtd.resetPerson();
    });

    public void setTextblock(String text) {
        textblock.setValue(text);
    }

    /**
    public LiveData<String> getMrzTextBlock() {
        return mrzTextBlock;
    }
     */

    public LiveData<Person> getPerson() {
        return person;
    }
}
