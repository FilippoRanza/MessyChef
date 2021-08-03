package com.example.messychef.storage_facility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class RecipeCacheInfo {

    private final int fileName;
    private int currStep;


    RecipeCacheInfo(int fileName) {
        this(fileName, 0);
    }

    RecipeCacheInfo(int fileName, int currStep) {
        this.fileName = fileName;
        this.currStep = currStep;
    }


    void store(OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeInt(fileName);
        dos.writeInt(currStep);
        os.close();
    }

    static RecipeCacheInfo load(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        int fileName = dis.readInt();
        int currStep = dis.readInt();
        RecipeCacheInfo output = new RecipeCacheInfo(fileName, currStep);
        dis.close();
        return output;
    }


    int getRecipeIndex() {
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
