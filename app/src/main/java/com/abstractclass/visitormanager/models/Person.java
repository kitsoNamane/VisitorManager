package com.abstractclass.visitormanager.models;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Person implements Serializable {

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    private String lastName;

    @ColumnInfo(name = "national_id")
    @SerializedName("national_id")
    private String nationalId;

    @ColumnInfo(name = "passport_number")
    @SerializedName("passport_number")
    private String passportNumber;

    @ColumnInfo(name = "sex")
    @SerializedName("sex")
    private String sex;

    @ColumnInfo(name = "birth_date")
    @SerializedName("birth_date")
    private String birthDate;

    @ColumnInfo(name = "nationality")
    @SerializedName("nationality")
    private String nationality;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

