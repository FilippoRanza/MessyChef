package com.example.messychef.storage_facility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class RecipeCacheInfo {

    private final String fileName;
    private int currStep;


    RecipeCacheInfo(String fileName) {
        this(fileName, 0);
    }

    RecipeCacheInfo(String fileName, int currStep) {
        this.fileName = fileName;
        this.currStep = currStep;
    }


    void store(OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF(fileName);
        dos.writeInt(currStep);
        os.close();
    }

    static RecipeCacheInfo load(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        String fileName = dis.readUTF();
        int currStep = dis.readInt();
        RecipeCacheInfo output = new RecipeCacheInfo(fileName, currStep);
        dis.close();
        return output;
    }


    String getFileName() {
        return fileName;
    }

    int getCurrStep() {
        return currStep;
    }


    RecipeCacheInfo setCurrStep(int step) {
        currStep = step;
        return this;
    }

}
