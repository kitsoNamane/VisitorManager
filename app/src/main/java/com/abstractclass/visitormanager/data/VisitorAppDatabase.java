package com.abstractclass.visitormanager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.abstractclass.visitormanager.models.Visitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Visitor.class}, version = 1, exportSchema = false)
public abstract class VisitorAppDatabase extends RoomDatabase {

    public abstract VisitorDao visitorDao();


    private static volatile VisitorAppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static VisitorAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VisitorAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VisitorAppDatabase.class, "visitor_app_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
