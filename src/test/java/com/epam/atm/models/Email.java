package com.epam.atm.models;

import com.epam.atm.utils.Utils;

public class Email {
    private static final String TO_TEXT = "max.yermachonak@gmail.com";
    private static final String BODY_TEXT = "Hi, I'm testing auto tests";
    private String subjectText;

    public String getToText(){
        return TO_TEXT;
    }

    public String getBodyText(){
        return BODY_TEXT;
    }

    public String getSubjectText(){
        if (subjectText == null || subjectText == "")
        subjectText = Utils.generateRandomString(12);
        return subjectText;
    }
}
