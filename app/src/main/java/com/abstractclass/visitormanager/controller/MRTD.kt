package com.abstractclass.visitormanager.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.abstractclass.visitormanager.models.Person
import java.sql.Timestamp
import java.util.*
import java.util.regex.Pattern

class MRTD {
    private val validator: String? = "([A|C|I][A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{9})([0-9]{1})([A-Z0-9<]{15})\n([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z]{3})([A-Z0-9<]{11})([0-9]{1})\n([A-Z0-9<]{30})"
    private var person: Person?
    private val personMutableLiveData: MutableLiveData<Person?>?
    private val mrtdText: MutableLiveData<String?>?
    private var mrtd: String? = null
    fun getMrtdText(): MutableLiveData<String?>? {
        return mrtdText
    }

    fun resetMrtdText(): MutableLiveData<String?>? {
        mrtdText?.postValue(null)
        return mrtdText
    }

    fun resetPerson(): MutableLiveData<Person?>? {
        personMutableLiveData?.postValue(null)
        return personMutableLiveData
    }

    fun getPerson(): MutableLiveData<Person?>? {
        return personMutableLiveData
    }

    fun isValidTD1(block: String?): Boolean {
        val pattern = Pattern.compile(validator!!)
        val matcher = pattern.matcher(block!!)
        if (matcher.matches() == false) {
            Log.d("Block test", " Failed : $block")
            return false
        }
        Log.d("Block test", " Success : $block")
        mrtdText?.postValue(block)
        mrtd = block
        decodeTD1()
        return true
    }

    fun decodeTD1() {
        if (person == null) person = Person()
        val mrtdGroupList = Arrays.asList<String?>(*mrtd?.split("\n")?.toTypedArray()!!)
        decodeTD1FirstGroup(mrtdGroupList[0])
        decodeTD1SecondGroup(mrtdGroupList[1])
        decodeTD1ThirdGroup(mrtdGroupList[2])
    }

    fun decodeTD1FirstGroup(group: String?) {
        var i = group?.indexOf("<")!!
        i += 2
        val j = group.indexOf("<", i)
        person?.nationalId = group.substring(i, j)
    }

    fun decodeTD1SecondGroup(group: String?) { // birthdate
        val idDateString = group?.substring(0, 6)
        var year = idDateString?.substring(0, 2)
        val month = idDateString?.substring(2, 4)
        val day = idDateString?.substring(4, idDateString.length)
        val checkYear = year?.toInt()
        year = if (checkYear!! >= 0 && checkYear <= 20) {
            "20$year"
        } else {
            "19$year"
        }
        val formatIdDateString = String.format("%s-%s-%s 00:00:00", year, month, day)
        val timestamp = Timestamp.valueOf(formatIdDateString)
        person?.birthDate = timestamp.time
        // gender
        val gender = group?.get(7).toString()

        if (gender.toLowerCase() == "m") {
            person?.sex = "male"
        } else if (gender.toLowerCase() == "f") {
            person?.sex = "female"
        } else {
            person?.sex = "undefined"
        }
    }

    fun decodeTD1ThirdGroup(group: String?) {
        var i = group?.indexOf("<")!!
        person?.lastName = group.substring(0, i)
        i += 2
        group.replaceFirst("<".toRegex(), "_")
        group.replaceFirst("<".toRegex(), "_")
        var j = group.indexOf("<", i)
        group.replaceFirst("<".toRegex(), "_")
        person?.firstName = group.substring(i, j)
        i = j + 1
        j = group.indexOf("<", i)
        person?.middleName = group.substring(i, j)
        personMutableLiveData?.postValue(person)
    }

    init {
        mrtdText = MutableLiveData()
        personMutableLiveData = MutableLiveData()
        person = Person()
    }
}