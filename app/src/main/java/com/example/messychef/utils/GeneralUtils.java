package com.example.messychef.utils;

public class GeneralUtils {

    private GeneralUtils() {
    }


    public static Integer parseInteger(CharSequence cs) {
        return (cs.length() == 0) ? null : parseInt(cs);
    }

    private static Integer parseInt(CharSequence cs) {
        return Integer.parseInt(cs.toString());
    }


}
