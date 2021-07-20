package com.example.messychef.storage_facility;

import android.app.Activity;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.serde.RecipeDeserializer;
import com.example.messychef.recipe.serde.RecipeSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class StoreData {

    private static final String RECIPE_IDENTIFIER = "recipe";

    private final IOFacility facility;
    private final FileIndexManager indexManager;


    public StoreData(Activity owner) {
        facility = new IOFacility(owner);
        indexManager = new FileIndexManager(owner);
    }

    public Recipe loadRecipe(FileInfo info) throws IOException, ClassNotFoundException {
        String fileName = info.getFileName();
        DataInputStream dis = new DataInputStream(facility.openReadFile(fileName));
        Recipe recipe = new RecipeDeserializer(dis).deserialize();
        dis.close();
        return recipe;
    }


    public void saveRecipe(Recipe r) throws IOException {
        String fileName = makeFileName(r);
        DataOutputStream dos = new DataOutputStream(facility.openWriteFile(fileName));
        new RecipeSerializer(dos).serialize(r);
        dos.close();
    }

    public void deleteFile(FileInfo info) {
        facility.deleteFile(info.getFileName());
    }

    public FileInfo[] buildFileInfoList() throws IOException {
        return  Arrays.stream(facility.getFileList())
                .filter(this::isRecipe)
                .map(this::fromFileName)
                .toArray(FileInfo[]::new);
    }

    private boolean isRecipe(String name) {
        return name.startsWith(RECIPE_IDENTIFIER);
    }

    private FileInfo fromFileName(String name) {
        int beginIndex = RECIPE_IDENTIFIER.length() + 1;
        int endIndex = name.lastIndexOf('-');
        String recipeName = name.substring(beginIndex, endIndex);
        return new FileInfo(name, recipeName);
    }


    private String makeFileName(Recipe r) throws  IOException {
        int index = indexManager.getNextIndex();
        return String.format("%s-%s-%d", RECIPE_IDENTIFIER, r.getName(), index);
    }

}
