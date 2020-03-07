package com.abstractclass.visitormanager.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.abstractclass.visitormanager.models.Visitor

@Dao
interface VisitorDao {
    @Insert
    fun addVisitor(visitor: Visitor?)

    @Query("SELECT * FROM visitor_table ORDER BY time_in ASC")
    fun getAllVisitors(): LiveData<List<Visitor?>>?

    @Query("SELECT * FROM visitor_table WHERE national_id = :nationalId ORDER BY id LIMIT 1")
    fun getVisitor(nationalId: String): Visitor

    @Query("UPDATE visitor_table SET time_out= :timeOut WHERE id= :id")
    fun signOut(timeOut : Long, id : Int)

}