package com.example.messychef.storage_facility;

import android.app.Activity;

import com.example.messychef.recipe.Recipe;

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




    public void saveRecipe(Recipe r) throws IOException {
        String fileName = makeFileName(r);
        ObjectOutputStream oos = new ObjectOutputStream(facility.openWriteFile(fileName));
        oos.writeObject(r);
        oos.close();
    }

    public ArrayList<Recipe> loadRecipes() throws IOException, ClassNotFoundException {
        IOFacility.FileIterator iterator = facility.getFiles();
        ArrayList<Recipe> output = new ArrayList<>(iterator.getCount());

        while(iterator.hasNext()) {
            ObjectInputStream ois = iterator.next();
            Recipe r = (Recipe) ois.readObject();
            output.add(r);
            ois.close();
        }

        return output;
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
