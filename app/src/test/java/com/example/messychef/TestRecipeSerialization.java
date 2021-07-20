package com.example.messychef;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.serde.RecipeDeserializer;
import com.example.messychef.recipe.serde.RecipeSerializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestRecipeSerialization {

    private static final int INGREDIENTS = 10;
    private static final int STEPS = 40;

    private static final String RECIPE_FILE_NAME = "recipe_file.dat";




    @Test
    public void testRecipeSerialization() {
        Path tempDir = makeTempDir();
        Recipe originalRecipe = new FakeRecipeFactory().makeRandomRecipe(INGREDIENTS, STEPS);
        writeRecipe(tempDir, originalRecipe);
        Recipe newRecipe = readRecipe(tempDir);
        assertEquals(originalRecipe, newRecipe);
    }



    private Path makeTempDir() {
        Path path = null;
        try {
            path = Files.createTempDirectory("test");
        } catch (IOException e) {
            AssertJUnit.fail(e.getMessage());
        }
        return path;
    }

    private Recipe readRecipe(Path path) {
        Recipe recipe = null;
        try {
            InputStream is = new FileInputStream(getRecipeFileName(path));
            DataInputStream dis = new DataInputStream(is);
            RecipeDeserializer deserializer = new RecipeDeserializer(dis);
            recipe = deserializer.deserialize();
            dis.close();
        } catch (IOException e) {
            AssertJUnit.fail(e.getMessage());
        }
        return recipe;
    }


    private void writeRecipe(Path p, Recipe recipe) {
        try {
            OutputStream os = new FileOutputStream(getRecipeFileName(p));
            DataOutputStream dos = new DataOutputStream(os);
            RecipeSerializer serializer = new RecipeSerializer(dos);
            serializer.serialize(recipe);
            dos.close();
        } catch (IOException e) {
            AssertJUnit.fail(e.getMessage());
        }
    }


    private File getRecipeFileName(Path p) {
        Path recipeFile = Paths.get(p.toString(), RECIPE_FILE_NAME);
        return recipeFile.toFile();
    }
}
