package com.example.messychef.storage_facility;

import android.app.Activity;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.serde.RecipeDeserializer;
import com.example.messychef.recipe.serde.RecipeSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class StoreData {

    private static final String RECIPE_IDENTIFIER = "recipe";

    private final IOFacility facility;
    private final FileIndexManager indexManager;


    public StoreData(Activity owner) {
        facility = new IOFacility(owner);
        indexManager = new FileIndexManager(owner);
    }


    public Recipe loadRecipe(FileInfo info) throws IOException {
        return loadRecipe(info.getFileName());
    }


    public Recipe loadRecipe(String fileName) throws IOException {
        DataInputStream dis = new DataInputStream(facility.openReadFile(fileName));
        Recipe recipe = new RecipeDeserializer(dis).deserialize();
        dis.close();
        return recipe;
    }


    public FileInfo updateRecipe(Recipe r, String currName) throws IOException {
        facility.deleteFile(currName);
        return saveRecipe(r);
    }


    public FileInfo saveRecipe(Recipe r) throws IOException {
        return saveRecipe(r, false);
    }

    protected FileInfo saveRecipe(Recipe r, boolean leaveOpen) throws IOException {
        String fileName = makeFileName(r);
        DataOutputStream dos = new DataOutputStream(facility.openWriteFile(fileName));
        new RecipeSerializer(dos).serialize(r);
        if (!leaveOpen)
            dos.close();
        return new FileInfo(fileName, r.getName());
    }

    public void deleteFile(String fileName) {
        facility.deleteFile(fileName);
    }

    public FileInfo[] buildFileInfoList() throws IOException {
        return Arrays.stream(facility.getFileList())
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


    private String makeFileName(Recipe r) throws IOException {
        int index = indexManager.getNextIndex();
        return String.format("%s-%s-%d", RECIPE_IDENTIFIER, r.getName(), index);
    }

}
