package com.abstractclass.visitormanager.models

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Person(
    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    var firstName: String? = null,

    @ColumnInfo(name = "middle_name")
    @SerializedName("middle_name")
    var middleName: String? = null,

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    var lastName: String? = null,

    @ColumnInfo(name = "national_id")
    @SerializedName("national_id")
    var nationalId: String? = null,

    @ColumnInfo(name = "passport_number")
    @SerializedName("passport_number")
    var passportNumber: String? = null,

    @ColumnInfo(name = "sex")
    @SerializedName("sex")
    var sex: String? = null,

    @ColumnInfo(name = "birth_date")
    @SerializedName("birth_date")
    var birthDate: Long = 0,

    @ColumnInfo(name = "nationality")
    @SerializedName("nationality")
    var nationality: String? = null
) : Serializable

/**
class Person : Serializable {
    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    private var firstName: String? = null
    @ColumnInfo(name = "middle_name")
    @SerializedName("middle_name")
    private var middleName: String? = null
    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    private var lastName: String? = null
    @ColumnInfo(name = "national_id")
    @SerializedName("national_id")
    private var nationalId: String? = null
    @ColumnInfo(name = "passport_number")
    @SerializedName("passport_number")
    private var passportNumber: String? = null
    @ColumnInfo(name = "sex")
    @SerializedName("sex")
    private var sex: String? = null
    @ColumnInfo(name = "birth_date")
    @SerializedName("birth_date")
    private var birthDate: Long = 0
    @ColumnInfo(name = "nationality")
    @SerializedName("nationality")
    private var nationality: String? = null

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String?) {
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String?) {
        this.lastName = lastName
    }

    fun getNationalId(): String? {
        return nationalId
    }

    fun setNationalId(nationalId: String?) {
        this.nationalId = nationalId
    }

    fun getPassportNumber(): String? {
        return passportNumber
    }

    fun setPassportNumber(passportNumber: String?) {
        this.passportNumber = passportNumber
    }

    fun getSex(): String? {
        return sex
    }

    fun setGender(fifth_char: Int) {
        when (fifth_char) {
            0 -> setSex("female")
            1 -> setSex("male")
        }
    }

    fun setSex(sex: String?) {
        if (sex?.toLowerCase() == "m") {
            this.sex = "male"
        } else if (sex?.toLowerCase() == "f") {
            this.sex = "female"
        } else {
            this.sex = "undefined"
        }
    }

    fun getBirthDate(): Long {
        return birthDate
    }

    fun setBirthDate(birthDate: Long) {
        this.birthDate = birthDate
    }

    fun getNationality(): String? {
        return nationality
    }

    fun setNationality(nationality: String?) {
        this.nationality = nationality
    }

    fun getMiddleName(): String? {
        return middleName
    }

    fun setMiddleName(middleName: String?) {
        this.middleName = middleName
    }

    override fun toString(): String {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", birthDate=" + birthDate +
                ", nationality='" + nationality + '\'' +
                '}'
    }
}
 */