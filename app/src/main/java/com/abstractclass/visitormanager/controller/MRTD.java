package com.abstractclass.visitormanager.controller;

import java.util.List;
import java.util.regex.Pattern;

public class MRTD
{
    List<String> doc_strings;
    String doc_string;
    public MRTD(String doc_string)
    {
        this.doc_string = doc_string;
    }

    public static boolean isValidTD1(String block)
    {
        String regex = "I.[A-Z0<]{3}[A-Z0-9]{1,9}<?[0-9O]{1}[A-Z0-9<]{14,22}\\n[0-9O]{7}(M|F)[0-9O]{7}[A-Z0<]{3}[A-Z0-9<]{11}[0-9O]\\n([A-Z0]+<)+<([A-Z0]+<)+<+";
        return block.matches(regex);
    }
}
