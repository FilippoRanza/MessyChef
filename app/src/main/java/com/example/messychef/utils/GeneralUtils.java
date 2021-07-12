package com.example.messychef.utils;

public class GeneralUtils {

    private GeneralUtils() {
    }


    public static Double parseDouble(CharSequence cs) {
        return (cs.length() == 0) ? null : Double.parseDouble(cs.toString());
    }


    private static Integer parseInt(CharSequence cs) {
        return Integer.parseInt(cs.toString());
    }


}
