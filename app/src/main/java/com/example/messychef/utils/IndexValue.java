package com.example.messychef.utils;

public class IndexValue<T> {
    final private T value;
    final private int index;

    public IndexValue(T value, int index) {
        this.value = value;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public T getValue() {
        return value;
    }
}
