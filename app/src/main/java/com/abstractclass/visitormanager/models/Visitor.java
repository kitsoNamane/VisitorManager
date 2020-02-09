package com.abstractclass.visitormanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Visitor extends Person implements Serializable {
    @Embedded
    @SerializedName("person")
    public Person person;

    @ColumnInfo(name = "purpose")
    @SerializedName("purpose")
    private String purpose;

    @ColumnInfo(name = "time_in")
    @SerializedName("time_in")
    private int timeIn;

    @ColumnInfo(name = "time_out")
    @SerializedName("time_out")
    private int timeOut;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(int timeIn) {
        this.timeIn = timeIn;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
}
