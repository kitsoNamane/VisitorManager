package com.abstractclass.visitormanager.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abstractclass.visitormanager.models.Visitor
import com.abstractclass.visitormanager.repository.VisitorRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class VisitorViewModel : ViewModel() {
    private var visitorRepository: VisitorRepository? = null
    private val visitors: LiveData<Visitor?>? = null
    fun initialize(context: Context) {
        visitorRepository = VisitorRepository(context)
    }

    fun getVisitors(): LiveData<List<Visitor?>>? {
        return visitorRepository?.getVisitors()
    }

    fun getVisitor(id: String) : Visitor {
        return visitorRepository?.findVisitor(id)!!
    }

    fun addVisitor(visitor: Visitor?) {
        GlobalScope.launch {
            visitorRepository?.addVisitor(visitor)
        }
    }
}