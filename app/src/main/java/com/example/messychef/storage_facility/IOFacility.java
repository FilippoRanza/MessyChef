package com.example.messychef.storage_facility;

import android.app.Activity;
import android.content.Context;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

class IOFacility {

    public void deleteFile(String fileName) {
        owner.deleteFile(fileName);
    }


    private final Context owner;

    IOFacility(Context owner) {
        this.owner = owner;
    }


    String[] getFileList() {
        return owner.fileList();
    }

    boolean exists(String name) {
        return getFile(name).exists();
    }

    int getFileSize(String name) {
        return (int) getFile(name).length();
    }

    private File getFile(String name) {
        File directory = owner.getFilesDir();
        Path path = Paths.get(directory.getAbsolutePath(), name);
        return path.toFile();
    }


    DataOutputStream openDataOutput(String name) throws IOException {
        return new DataOutputStream(openWriteFile(name));
    }


    DataInputStream openDataInput(String name) throws IOException {
        return new DataInputStream(openReadFile(name));
    }


    InputStream openReadFile(String name) throws IOException {
        InputStream is;
        try {
            is = owner.openFileInput(name);
        } catch (FileNotFoundException e) {
            openWriteFile(name);
            is = owner.openFileInput(name);
        }
        return is;
    }

    OutputStream openWriteFile(String name) throws IOException {
        return owner.openFileOutput(name, Activity.MODE_PRIVATE);
    }


}
