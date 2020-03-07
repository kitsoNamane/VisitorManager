package com.abstractclass.visitormanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abstractclass.visitormanager.models.Visitor
import java.util.concurrent.Executors

@Database(entities = [Visitor::class], version = 2, exportSchema = false)
abstract class VisitorAppDatabase : RoomDatabase() {
    abstract fun visitorDao(): VisitorDao?

    companion object {
        @Volatile
        private var INSTANCE: VisitorAppDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context): VisitorAppDatabase? {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        VisitorAppDatabase::class.java,
                        "visitor_app_db"
                ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}