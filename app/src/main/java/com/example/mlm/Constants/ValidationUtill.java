package com.example.mlm.Constants;

import java.util.regex.Pattern;

public class ValidationUtill {

    private static final String EMAIL_PATTERN =
            "^[a-z0-9_]+(?:\\.[a-z0-9]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
    private static final String PHONE_NUMBER =
            "^(([0-9+]{6,20}))$";
    private static final String ACCOUNT_PATTERN = "[0-9]{9,18}";

    public static boolean isValidEmail(String email) {
        return (Pattern.compile(EMAIL_PATTERN).matcher(email).matches());
    }

    public static boolean isValidPhoneNumber(String phone) {
//        String num = "0123456789";
        if (phone.indexOf("+") > 0)
            return false;
        return (Pattern.compile(PHONE_NUMBER).matcher(phone).matches());
    }

    public static boolean isValidAccountNo(String accno) {
        return (Pattern.compile(ACCOUNT_PATTERN).matcher(accno).matches());
    }

}
