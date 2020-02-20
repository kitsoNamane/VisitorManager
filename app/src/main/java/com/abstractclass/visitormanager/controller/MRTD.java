package com.abstractclass.visitormanager.controller;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.abstractclass.visitormanager.models.Person;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MRTD {
    private String validator = "([A|C|I][A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{9})([0-9]{1})([A-Z0-9<]{15})\n([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z]{3})([A-Z0-9<]{11})([0-9]{1})\n([A-Z0-9<]{30})";

    private Person person;
    private MutableLiveData<Person> personMutableLiveData;
    private MutableLiveData<String> mrtdText;
    private String mrtd;

    public MRTD() {
        mrtdText = new MutableLiveData<>();
        personMutableLiveData = new MutableLiveData<>();
        person = new Person();
    }

    public MutableLiveData<String> getMrtdText() {
        return mrtdText;
    }

    public MutableLiveData<String> resetMrtdText() {
        mrtdText.postValue(null);
        return mrtdText;
    }

    public MutableLiveData<Person> resetPerson() {
        personMutableLiveData.postValue(null);
        return personMutableLiveData;
    }

    public MutableLiveData<Person> getPerson() {
        return personMutableLiveData;
    }

    public boolean isValidTD1(String block) {
        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(block);
        if(matcher.matches() == false) {
            Log.d("Block test", " Failed : "+block);
            return false;
        }
        Log.d("Block test", " Success : "+block);
        mrtdText.postValue(block);
        mrtd = block;
        decodeTD1();
        return true;
    }

    public void decodeTD1() {
        if(person == null) person = new Person();
        List<String> mrtdGroupList = Arrays.asList(mrtd.split("\n"));
        //Iterator<String> mrtdGroups = mrtdGroupList.iterator();
        decodeTD1FirstGroup(mrtdGroupList.get(0));
        decodeTD1SecondGroup(mrtdGroupList.get(1));
        decodeTD1ThirdGroup(mrtdGroupList.get(2));
    }

    public void decodeTD1FirstGroup(String group) {
        int i = group.indexOf("<");
        //group.replaceFirst("<", "_");
        i += 2;
        int j = group.indexOf("<", i);
        person.setNationalId(group.substring(i, j).toString());
    }

    public void decodeTD1SecondGroup(String group) {
        // birthdate
        String idDateString = group.substring(0, 6);
        String year = idDateString.substring(0, 2);
        String month = idDateString.substring(2, 4);
        String day = idDateString.substring(4, idDateString.length());
        int checkYear = Integer.parseInt(year);

        if(checkYear >= 0 && checkYear <= 20) {
            year = "20"+year;
        } else {
            year = "19"+year;
        }

        String formatIdDateString = String.format("%s-%s-%s 00:00:00", year, month, day);
        Timestamp timestamp = Timestamp.valueOf(formatIdDateString);
        person.setBirthDate(timestamp.getTime());

        // gender
        String gender = String.valueOf(group.charAt(7));
        person.setSex(gender);
    }

    public void decodeTD1ThirdGroup(String group) {
        int i = group.indexOf("<");
        person.setLastName(group.substring(0, i));
        i += 2;
        group.replaceFirst("<", "_");
        group.replaceFirst("<", "_");
        int j = group.indexOf("<", i);
        group.replaceFirst("<", "_");
        person.setFirstName(group.substring(i, j));
        i = j + 1;
        j = group.indexOf("<", i);
        person.setMiddleName(group.substring(i, j));
        personMutableLiveData.postValue(person);
    }


}
