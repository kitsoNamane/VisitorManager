package com.abstractclass.visitormanager.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Visitor implements Serializable {
    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("national_id")
    private String nationalId;

    @SerializedName("passport_number")
    private String passportNumber;

    @SerializedName("sex")
    private String sex;

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("purpose")
    private String purpose;

    @SerializedName("time_in")
    private int timeIn;

    @SerializedName("time_out")
    private int timeOut;
}
