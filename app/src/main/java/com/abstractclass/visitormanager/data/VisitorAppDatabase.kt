package com.abstractclass.visitormanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abstractclass.visitormanager.models.Visitor
import java.util.concurrent.Executors

@Database(entities = [Visitor::class], version = 1, exportSchema = false)
abstract class VisitorAppDatabase : RoomDatabase() {
    abstract fun visitorDao(): VisitorDao?

    companion object {
        @Volatile
        private var INSTANCE: VisitorAppDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)
        fun getDatabase(context: Context?): VisitorAppDatabase? {
            if (INSTANCE == null) {
                synchronized(VisitorAppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = VisitorAppDatabase::class.java.let {
                            Room.databaseBuilder<VisitorAppDatabase>(context!!.getApplicationContext(),
                                    it, "visitor_app_db")
                                    .build()
                        }
                    }
                }
            }
            return INSTANCE
        }
    }
}