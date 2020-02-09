package com.abstractclass.visitormanager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.abstractclass.visitormanager.models.Visitor;

import java.util.List;

@Dao
public interface VisitorDao {
   @Insert
   public void addVisitor(Visitor visitor);

   @Query("SELECT * FROM visitor_table ORDER BY time_in ASC")
   LiveData<List<Visitor>> getAllVisitors();
}
