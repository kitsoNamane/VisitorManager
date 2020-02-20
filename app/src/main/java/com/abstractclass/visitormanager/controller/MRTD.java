package com.abstractclass.visitormanager.controller;

import android.util.Log;

import com.abstractclass.visitormanager.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MRTD
{
    String[] docStrings;
    String docString;
    public static List<String> TD1lines = new ArrayList<String>();

    public MRTD(String docString)
    {
        this.docString = docString;
        this.buildDocArray();

    }

    public static boolean isValidTD1(String block)
    {
        /**
        TD1lines.add("([A|C|I][A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{9})([0-9]{1})([A-Z0-9<]{15})");
        TD1lines.add("([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z]{3})([A-Z0-9<]{11})([0-9]{1})");
        TD1lines.add("([A-Z0-9<]{30})");
         */

        String validator = "([A|C|I][A-Z0-9<]{1})([A-Z]{3})([A-Z0-9<]{9})([0-9]{1})([A-Z0-9<]{15})\n([0-9]{6})([0-9]{1})([M|F|X|<]{1})([0-9]{6})([0-9]{1})([A-Z]{3})([A-Z0-9<]{11})([0-9]{1})\n([A-Z0-9<]{30})";

        Pattern pattern = Pattern.compile(validator);
        Matcher matcher = pattern.matcher(block);
        Log.d("Block test", " Regex : "+validator);
        Log.d("Block test", " Regex : "+block);
        if(matcher.matches() == false) return false;
        /**
        String[] textLines = block.split("\n");
        for(int i = 0; i < 3; i++) {
            Pattern pattern = Pattern.compile(TD1lines.get(i));
            Matcher matcher = pattern.matcher(textLines[i].trim());
            Log.d("Block test", " Regex : "+TD1lines.get(i));
            Log.d("Block test", " Regex : "+textLines[i]);
            if(matcher.matches() == false) return false;
        }
         */
        return true;
    }

    private void buildDocArray()
    {
        this.docStrings = this.docString.split("\n");
        Log.d("Split", "The thing is split");
    }

    public Person getPerson()
    {
        int blockCounter = 0;
        Person person = new Person();
        for (String block: docStrings)
        {
            switch (blockCounter)
            {
                case 0:
                    person.setNationalId(block.substring(15, 23));
                    break;
                case 1:
                    break;
                case 2:
                    person.setLastName(block.substring(0, block.indexOf("<<")));
                    break;
            }

            blockCounter ++;
        }

        return person;
    }

}
