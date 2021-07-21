package com.example.messychef.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class GeneralUtils {

    private GeneralUtils() {
    }


    public static Double parseDouble(CharSequence cs) {
        return (cs.length() == 0) ? null : Double.parseDouble(cs.toString());
    }


    private static Integer parseInt(CharSequence cs) {
        return Integer.parseInt(cs.toString());
    }

    public static  <T> ArrayList<T> fromArray(T[] array){
        ArrayList<T> output = new ArrayList<>(array.length);
        output.addAll(Arrays.asList(array));
        return output;
    }

    public static  <T, K> ArrayList<K> fromArray(T[] array, Function<T, K> map){
        ArrayList<K> output = new ArrayList<>(array.length);
        Arrays.stream(array).map(map).forEach(output::add);
        return output;
    }



}
