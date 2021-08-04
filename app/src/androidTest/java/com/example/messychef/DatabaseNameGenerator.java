package com.example.messychef;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

class DatabaseNameGenerator implements Iterable<String> {

    private static final String BASE_DB_NAME = "DATABASE-%d";

    private final ArrayList<String> names;

    DatabaseNameGenerator() {
        names = new ArrayList<>();
    }


    synchronized String getNextName() {
        String name = String.format(BASE_DB_NAME, names.size());
        names.add(name);
        return name;
    }


    @NonNull
    @Override
    public Iterator<String> iterator() {
        return names.iterator();
    }
}
