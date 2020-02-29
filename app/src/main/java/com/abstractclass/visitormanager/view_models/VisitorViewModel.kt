package com.abstractclass.visitormanager.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abstractclass.visitormanager.models.Visitor
import com.abstractclass.visitormanager.repository.VisitorRepository

class VisitorViewModel : ViewModel() {
    private var visitorRepository: VisitorRepository? = null
    private val visitors: LiveData<MutableList<Visitor?>?>? = null
    fun init(context: Context?) {
        visitorRepository = VisitorRepository(context)
    }

    fun getVisitors(): LiveData<MutableList<Visitor?>?>? {
        return visitorRepository?.getVisitors()
    }

    fun addVisitor(visitor: Visitor?) {
        visitorRepository?.addVisitor(visitor)
    }
}