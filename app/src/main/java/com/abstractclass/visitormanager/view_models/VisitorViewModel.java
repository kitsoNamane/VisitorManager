package com.abstractclass.visitormanager.view_models;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.abstractclass.visitormanager.data.VisitorAppDatabase;
import com.abstractclass.visitormanager.models.Visitor;
import com.abstractclass.visitormanager.repository.VisitorRepository;

import java.util.List;

public class VisitorViewModel extends ViewModel {
    private VisitorRepository visitorRepository;

    private LiveData<List<Visitor>> visitors;

    public void init(Context context) {
        visitorRepository = new VisitorRepository(context);
    }

    public LiveData<List<Visitor>> getVisitors() {
        return visitorRepository.getVisitors();
    }

    public void addVisitor(Visitor visitor) {
        visitorRepository.addVisitor(visitor);
    }
}
