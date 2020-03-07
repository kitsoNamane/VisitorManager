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

    private val person: LiveData<Person?>? = Transformations.switchMap(textblock!!) { textblock: String? ->
        if (mrtd?.isValidTD1(textblock)!!) {
            return@switchMap mrtd.getPerson()
        } else {
            return@switchMap mrtd.resetPerson()
        }
    }

    fun setTextblock(text: String?) {
        textblock?.setValue(text)
    }

    fun getPerson(): LiveData<Person?>? {
        return person
    }
}