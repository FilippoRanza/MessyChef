package com.example.messychef.storage_facility;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class FileInfo implements Serializable {

    final String fileName;
    final String recipeName;

    public FileInfo(String fileName, String recipeName) {
        this.recipeName = recipeName;
        this.fileName = fileName;
    }

    @NonNull
    public String toString() {
        return this.recipeName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRecipeName() {
        return recipeName;
    }

}

