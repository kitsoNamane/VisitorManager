package com.abstractclass.visitormanager.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abstractclass.visitormanager.utils.Utils
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "visitor_table")
data class Visitor (
   @PrimaryKey(autoGenerate = false)
    var id: Int? = null,

   @SerializedName("person")
   @Embedded
   var person: Person? = null,

    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    var phone: String? = null,

    @ColumnInfo(name = "plate_number")
    @SerializedName("plate_number")
    var plateNumber: String? = null,

    @ColumnInfo(name = "purpose")
    @SerializedName("purpose")
    var purpose: String? = null,

    @ColumnInfo(name = "time_in")
    @SerializedName("time_in")
    var timeIn: Long? = null,

    @ColumnInfo(name = "time_out")
    @SerializedName("time_out")
    var timeOut: Long? = null

) : Serializable {
    fun toList() : List<String> {
        //"First Name", "Last Name", "National ID", "Phone Number", "Birthdate", "Gender", "Time In", "Purpose",
        //"Plate Number", "Time Out"
        return listOf(
                person?.firstName!!, person?.lastName!!, person?.nationalId!!, phone!!, Utils.timeStampToString(person?.birthDate!!),
                person?.sex!!, Utils.timeStampToString(timeIn!!), purpose!!, Utils.timeStampToString(timeOut!!)
        )
    }
}

/**
@Entity(tableName = "visitor_table")
class Visitor : Serializable {
    @PrimaryKey(autoGenerate = false)
    val id: Int
    @Embedded
    @SerializedName("person")
    private var person: Person? = null
    @ColumnInfo(name = "phone_number")
    @SerializedName("phone_number")
    private var phone: String? = null
    @ColumnInfo(name = "plate_number")
    @SerializedName("plate_number")
    private var plateNumber: String? = null
    @ColumnInfo(name = "purpose")
    @SerializedName("purpose")
    private var purpose: String? = null
    @ColumnInfo(name = "time_in")
    @SerializedName("time_in")
    private var timeIn = 0
    @ColumnInfo(name = "time_out")
    @SerializedName("time_out")
    private var timeOut = 0

    fun getPurpose(): String? {
        return purpose
    }

    fun setPurpose(purpose: String?) {
        this.purpose = purpose
    }

    fun getTimeIn(): Int {
        return timeIn
    }

    fun setTimeIn(timeIn: Int) {
        this.timeIn = timeIn
    }

    fun getTimeOut(): Int {
        return timeOut
    }

    fun setTimeOut(timeOut: Int) {
        this.timeOut = timeOut
    }

    fun getPerson(): Person? {
        return person
    }

    fun setPerson(person: Person?) {
        this.person = person
        setId(Integer.parseInt(person?.getNationalId()!!))
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

    fun getPlateNumber(): String? {
        return plateNumber
    }

    fun setPlateNumber(plateNumber: String?) {
        this.plateNumber = plateNumber
    }
}
 */