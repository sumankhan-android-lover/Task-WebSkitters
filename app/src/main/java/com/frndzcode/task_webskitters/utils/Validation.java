package com.frndzcode.task_webskitters.utils;

import android.util.Patterns;

public class Validation {

    public static boolean isEmailValid(String strEmail) {
        if (null != strEmail && !isStringEmpty(strEmail) && checkEmail(strEmail)) {
            return true;
        } else
            return false;
    }

    public static boolean isPhoneValid(String strPhone) {
        if (null != strPhone && !isStringEmpty(strPhone) && checkPhone(strPhone)) {
            return true;
        } else
            return false;
    }

    public static boolean isPasswordValid(String strEmail) {
        if (null != strEmail && !isStringEmpty(strEmail) && checkPasswordLength(strEmail)) {
            return true;
        } else
            return false;
    }

    //is empty check
    public static boolean isStringEmpty(String editText) {
        if (null == editText)
            return true;
        else
            return editText.trim().isEmpty();
    }

    //Email Format Validation
    public static boolean checkEmail(String strEmail) {
        return Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    //Email Format Validation
    public static boolean checkPhone(String strPhone) {
        return Patterns.PHONE.matcher(strPhone).matches();
    }

    //Password length validation
    private static boolean checkPasswordLength(String strPass) {
        return strPass.trim().length() >= 6;
    }

}
