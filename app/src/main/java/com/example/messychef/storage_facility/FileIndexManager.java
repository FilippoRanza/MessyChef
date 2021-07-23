package com.example.messychef.storage_facility;

import android.app.Activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

class FileIndexManager {

    private static final String INDEX_FILE_NAME = "index-file";

    private final IOFacility facility;

    FileIndexManager(Activity owner) {
        facility = new IOFacility(owner);
    }

    int getNextIndex() throws IOException {
        int output = loadIndex();
        saveIndex(output + 1);
        return output;
    }


    private int loadIndex() throws IOException {
        DataInputStream dis = facility.openDataInput(INDEX_FILE_NAME);
        int output = 0;
        try {
            output = dis.readInt();
        } catch (EOFException ignored) {
        }
        dis.close();
        return output;
    }

    private void saveIndex(int index) throws IOException {
        DataOutputStream dos = facility.openDataOutput(INDEX_FILE_NAME);
        dos.writeInt(index);
        dos.close();
    }


}
