package com.abstractclass.visitormanager.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.abstractclass.visitormanager.data.VisitorAppDatabase
import com.abstractclass.visitormanager.data.VisitorDao
import com.abstractclass.visitormanager.models.Visitor

class VisitorRepository(context: Context) {
    private var visitorDao: VisitorDao?
    private var visitors: LiveData<List<Visitor?>>? = null
    private var visitor: Visitor? = null
    // Room executes all queries on a separate thread.
// Observed LiveData will notify the observer when the data has changed.
    fun getVisitors(): LiveData<List<Visitor?>>? {
        return visitors
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
// that you're not doing any long running operations on the main thread, blocking the UI.
    suspend fun addVisitor(visitor: Visitor?) {
        VisitorAppDatabase.databaseWriteExecutor.execute {
            visitorDao?.addVisitor(visitor)
        }
        //VisitorAppDatabase.Companion.databaseWriteExecutor.execute(Runnable { visitorDao?.addVisitor(visitor) })
    }

    fun signOut(timeOut : Long, id : Int) {
        visitorDao?.signOut(timeOut, id)
    }

    fun findVisitor(id: String) : Visitor? {
        val visitor = visitorDao?.getVisitor(id)!!
        if(visitor == null) {
            return null
        }
        return visitor
    }

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    init {
        val db: VisitorAppDatabase? = VisitorAppDatabase.Companion.getDatabase(context)
        visitorDao = db?.visitorDao()
        visitors = visitorDao?.getAllVisitors()
    }
}