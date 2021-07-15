package com.example.messychef.storage_facility;

public class FileInfo {

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

