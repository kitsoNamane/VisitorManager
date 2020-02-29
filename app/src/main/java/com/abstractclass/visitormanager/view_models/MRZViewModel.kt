package com.abstractclass.visitormanager.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.abstractclass.visitormanager.controller.MRTD
import com.abstractclass.visitormanager.models.Person

class MRZViewModel : ViewModel() {
    private val mrtd: MRTD? = MRTD()
    private val textblock: MutableLiveData<String?>? = MutableLiveData()
    /**
     * private LiveData<String> mrzTextBlock = Transformations.switchMap(textblock, textblock -> {
     * if(mrtd.isValidTD1(textblock)) {
     * return mrtd.getMrtdText();
     * }
     * return mrtd.resetMrtdText();
     * });
    </String> */

    private val person: LiveData<Person?>? = Transformations.switchMap(textblock!!) { textblock: String? ->
        if (mrtd?.isValidTD1(textblock)!!) {
            return@switchMap mrtd.getPerson()
        } else {
            mrtd.resetPerson()
        }
    }


    fun setTextblock(text: String?) {
        textblock?.setValue(text)
    }

    /**
     * public LiveData<String> getMrzTextBlock() {
     * return mrzTextBlock;
     * }
    </String> */
    fun getPerson(): LiveData<Person?>? {
        return person
    }
}