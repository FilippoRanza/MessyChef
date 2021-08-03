package com.example.messychef.storage_facility;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CurrentRecipe {


    private static final String CURR_RECIPE_FILE_NAME = "CURRENT-RECIPE-FILE";
    private final IOFacility facility;

    public CurrentRecipe(Context owner) {
        facility = new IOFacility(owner);
    }


    public void clear() {
        facility.deleteFile(CURR_RECIPE_FILE_NAME);
    }

    public void setCurrentRecipeName(int id) throws IOException {
        RecipeCacheInfo info = new RecipeCacheInfo(id);
        info.store(makeWriter());
    }

    public Integer getCurrentRecipeName() throws IOException {
        return (exists()) ? blindGetCurrentRecipeName() : null;
    }

    public Integer getCurrentRecipeIndex() throws IOException {
        return (exists()) ? blindGetCurrentRecipeIndex() : null;
    }

    public void setCurrentRecipeIndex(int index) throws IOException {
        load().setCurrStep(index).store(makeWriter());
    }


    private Integer blindGetCurrentRecipeIndex() throws IOException {
        return load().getCurrStep();
    }


    public int blindGetCurrentRecipeName() throws IOException {
        return load().getRecipeIndex();
    }


    private RecipeCacheInfo load() throws IOException {
        return RecipeCacheInfo.load(makeReader());
    }


    private OutputStream makeWriter() throws IOException {
        return facility.openWriteFile(CURR_RECIPE_FILE_NAME);
    }

    private InputStream makeReader() throws IOException {
        return facility.openDataInput(CURR_RECIPE_FILE_NAME);
    }

    private boolean exists() {
        return facility.exists(CURR_RECIPE_FILE_NAME);
    }
}
