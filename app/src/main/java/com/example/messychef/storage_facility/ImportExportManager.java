package com.example.messychef.storage_facility;

import android.app.Activity;
import android.net.Uri;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.serde.RecipeDeserializer;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImportExportManager {

    private final StoreData storeData;
    private final IOFacility facility;

    private ImportExportManager(Activity owner) {
        facility = new IOFacility(owner);
        this.storeData = new StoreData(owner);
    }

    public static void exportRecipes(Uri uri, Activity owner) throws IOException {
        new ImportExportManager(owner).exportRecipes(owner.getContentResolver().openOutputStream(uri));
    }

    public static void importRecipes(Uri uri, Activity owner) throws IOException {
        new ImportExportManager(owner).importRecipes(owner.getContentResolver().openInputStream(uri));

    }

    private void exportRecipes(OutputStream outputStream) throws IOException {

        DataOutputStream dos = new DataOutputStream(outputStream);
        FileInfo[] infoList = storeData.buildFileInfoList();
        dos.writeInt(infoList.length);
        for (FileInfo info : infoList) {
            String name = info.getFileName();
            byte[] data = loadRawRecipe(name);
            dos.write(data);
        }
        dos.close();
    }

    private void importRecipes(InputStream inputStream) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            Recipe r = new RecipeDeserializer(dis).deserialize();
            storeData.saveRecipe(r, true);
        }
        dis.close();
    }

    private byte[] loadRawRecipe(String name) throws IOException {
        DataInput input = facility.openDataInput(name);
        byte[] out = new byte[facility.getFileSize(name)];
        input.readFully(out);
        return out;
    }

}
