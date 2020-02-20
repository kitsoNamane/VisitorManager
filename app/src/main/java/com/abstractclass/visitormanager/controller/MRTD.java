package com.abstractclass.visitormanager.controller;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.abstractclass.visitormanager.models.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MRTD {
    private Person person;

    String validator = "([A|C|I][A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{9})([0-9]{1})([A-Z0-9<]{15})\n([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z]{3})([A-Z0-9<]{11})([0-9]{1})\n([A-Z0-9<]{30})";
    private static List<String> TD1lines = new ArrayList<String>();
    private MutableLiveData<String> mrtdText;

    public MRTD() {
        mrtdText = new MutableLiveData<>();
    }

    public MutableLiveData<String> getMrtdText() {
        return mrtdText;
    }

    public MutableLiveData<String> resetMrtdText() {
        mrtdText.postValue(null);
        return mrtdText;
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
        return true;
    }

    public void decodeTD1() {
        if(person == null) person = new Person();
        List<String> mrtdGroupList = Arrays.asList(mrtdText.getValue().split("\n"));
        Iterator<String> mrtdGroups = mrtdGroupList.iterator();
    }

    public void decodeTD1FirstGroup(String group) {
        int i = group.indexOf("<");
        group.replaceFirst("<", "_");
        i += 2;
        int j = group.indexOf("<");
        person.setNationalId(group.substring(i, j).toString());
    }

    public void decodeTD1SecondGroup(String group) {
        String idDateString = group.substring(0, 5);


    }

    public void decodeTD1ThirdGroup(String group) {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
