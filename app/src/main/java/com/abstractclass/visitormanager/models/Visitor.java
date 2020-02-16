package com.abstractclass.visitormanager.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "visitor_table")
public class Visitor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    @SerializedName("person")
    private Person person;

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    private String phone;

    @ColumnInfo(name = "plate_number")
    @SerializedName("plate_number")
    private String plateNumber;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}
