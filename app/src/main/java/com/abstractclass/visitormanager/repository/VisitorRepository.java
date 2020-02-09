package com.abstractclass.visitormanager.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.abstractclass.visitormanager.data.VisitorAppDatabase;
import com.abstractclass.visitormanager.data.VisitorDao;
import com.abstractclass.visitormanager.models.Visitor;

import java.util.List;

public class VisitorRepository {
    private VisitorDao visitorDao;
    private LiveData<List<Visitor>> allVisitors;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    VisitorRepository(Application application) {
        VisitorAppDatabase db = VisitorAppDatabase.getDatabase(application);
        visitorDao = db.visitorDao();
        allVisitors = visitorDao.getAllVisitors();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Visitor>> getAllVisitors() {
        return allVisitors;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Visitor visitor) {
        VisitorAppDatabase.databaseWriteExecutor.execute(() -> {
            visitorDao.addVisitor(visitor);
        });
    }
}
