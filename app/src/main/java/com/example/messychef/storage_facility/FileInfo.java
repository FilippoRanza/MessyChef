package com.example.messychef.storage_facility;

import java.io.Serializable;

public class FileInfo implements Serializable {

    String fileName;
    String recipeName;

    public FileInfo(String fileName, String recipeName) {
        this.recipeName = recipeName;
        this.fileName = fileName;
    }

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

