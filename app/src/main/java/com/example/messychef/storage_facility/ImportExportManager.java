package com.example.messychef.storage_facility;

import android.content.Context;
import android.net.Uri;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.load_store.RecipeIterator;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.recipe.serde.RecipeDeserializer;
import com.example.messychef.recipe.serde.RecipeSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImportExportManager {


    public ImportExportManager() {
    }

    public static void exportRecipes(Uri uri, Context owner) throws IOException {
        new ImportExportManager()
                .exportRecipes(owner.getContentResolver().openOutputStream(uri), RecipeLoadStore.getInstance());
    }

    public static void importRecipes(Uri uri, Context owner) throws IOException {
        new ImportExportManager().importRecipes(owner.getContentResolver().openInputStream(uri), RecipeLoadStore.getInstance());
    }

    public void exportRecipes(OutputStream outputStream, RecipeLoadStore loadStore) throws IOException {

        DataOutputStream dos = new DataOutputStream(outputStream);
        RecipeIterator iterator = loadStore.getRecipeIterator();
        dos.writeInt(iterator.getRecipeCount());
        RecipeSerializer serializer = new RecipeSerializer(dos);
        for (Recipe recipe : iterator)
            serializer.serialize(recipe);

        dos.close();
    }

    public void importRecipes(InputStream inputStream, RecipeLoadStore loadStore) throws IOException {
        DataInputStream dis = new DataInputStream(inputStream);
        int count = dis.readInt();
        RecipeDeserializer deserializer = new RecipeDeserializer(dis);
        for (int i = 0; i < count; i++) {
            Recipe r = deserializer.deserialize();
            loadStore.saveRecipe(r);
        }
        dis.close();
    }

}
