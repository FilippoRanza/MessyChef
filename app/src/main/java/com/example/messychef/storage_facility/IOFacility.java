package com.example.messychef.storage_facility;

import android.app.Activity;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

class IOFacility {

    public void deleteFile(String fileName) {
        owner.deleteFile(fileName);
    }



    private final Activity owner;

    IOFacility(Activity owner) {
        this.owner = owner;
    }


    String[] getFileList() {
        return owner.fileList();
    }


    DataOutputStream openDataOutput(String name) throws IOException {
        return  new DataOutputStream(openWriteFile(name));
    }


    DataInputStream openDataInput(String name) throws IOException {
        return new DataInputStream(openReadFile(name));
    }


    ObjectOutputStream openObjectOutput(String name) throws IOException {
        return  new ObjectOutputStream(openWriteFile(name));
    }


    ObjectInputStream openObjectRead(String name) throws IOException {
        return new ObjectInputStream(openReadFile(name));
    }


    InputStream openReadFile(String name) throws IOException {
        InputStream is = null;
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
