package com.abstractclass.visitormanager.controller;

import android.util.Log;

import com.abstractclass.visitormanager.models.Person;

import java.util.List;
import java.util.regex.Pattern;

public class MRTD
{
    String[] doc_strings;
    String doc_string;
    public MRTD(String doc_string)
    {
        this.doc_string = doc_string;
        this.buildDocArray();
    }

    public static boolean isValidTD1(String block)
    {
        String[] strs = block.split("\n");
        return (strs[0].length() == 30);
        //String regex = "I.[A-Z0<]{3}[A-Z0-9]{1,9}<?[0-9O]{1}[A-Z0-9<]{14,22}\\n[0-9O]{7}(M|F)[0-9O]{7}[A-Z0<]{3}[A-Z0-9<]{11}[0-9O]\\n([A-Z0]+<)+<([A-Z0]+<)+<+";
        //return block.matches(regex);
    }

    private void buildDocArray()
    {
        this.doc_strings = this.doc_string.split("\n");
        Log.d("Split", "The thing is split");
    }

    public Person getPerson()
    {
        int block_counter = 0;
        Person person = new Person();
        for (String block:doc_strings)
        {
            switch (block_counter)
            {
                case 0:
                    person.setNationalId(block.substring(15, 23));
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }

            block_counter ++;
        }

        return person;
    }

}
