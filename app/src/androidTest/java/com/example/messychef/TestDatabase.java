package com.example.messychef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;
import com.example.messychef.recipe.load_store.RecipeIterator;
import com.example.messychef.recipe.load_store.RecipeLoadStore;
import com.example.messychef.storage_facility.ImportExportManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class TestDatabase {


    DatabaseNameGenerator nameGenerator;

    FakeRecipeFactory fakeRecipeFactory;

    @Before
    public void initialize() {
        fakeRecipeFactory = new FakeRecipeFactory();
        nameGenerator = new DatabaseNameGenerator();
    }

    @Test
    public void writeDataInDatabase() {
        Recipe recipe = fakeRecipeFactory.makeRandomRecipe(5, 10);
        RecipeLoadStore loadStore = initDatabase(nameGenerator.getNextName());
        loadStore.saveRecipe(recipe);
        Recipe newRecipe = loadStore.loadRecipeById(1);
        assertEquals(recipe, newRecipe);
    }


    @Test
    public void testRecipeNameList() {
        ArrayList<Recipe> recipes = buildRecipes(100);
        RecipeLoadStore loadStore = initDatabase(nameGenerator.getNextName());
        for (Recipe r : recipes) {
            loadStore.saveRecipe(r);
        }

        List<RecipeDao.RecipeInfo> recipeInfoList = loadStore.getRecipeList();
        assertEquals(recipeInfoList.size(), recipes.size());
        for (int i = 0; i < recipes.size(); i++) {
            RecipeDao.RecipeInfo info = recipeInfoList.get(i);
            Recipe recipe = recipes.get(i);
            assertEquals(recipe.getName(), info.getName());
            assertEquals(recipe.getRecipeID(), info.getRecipeID());
        }


    }


    @Test
    public void testDeleteRecipe() {
        Recipe recipe = fakeRecipeFactory.makeRandomRecipe(5, 10);
        RecipeLoadStore loadStore = initDatabase(nameGenerator.getNextName());
        loadStore.saveRecipe(recipe);
        List<RecipeDao.RecipeInfo> preDelete = loadStore.getRecipeList();
        assertEquals(preDelete.size(), 1);

        loadStore.deleteRecipe(recipe);
        List<RecipeDao.RecipeInfo> postDelete = loadStore.getRecipeList();
        assertEquals(postDelete.size(), 0);
    }

    @Test
    public void testUpdateRecipe() {
        Recipe origRecipe = fakeRecipeFactory.makeRandomRecipe(5, 10);
        RecipeLoadStore loadStore = initDatabase(nameGenerator.getNextName());
        loadStore.saveRecipe(origRecipe);
        origRecipe.setName("1234");
        loadStore.updateRecipe(origRecipe);

        Recipe newRecipe = loadStore.loadRecipeById(1);

        assertEquals(origRecipe, newRecipe);
    }


    @Test
    public void testRecipeIterator() {
        ArrayList<Recipe> recipes = buildRecipes(100);
        RecipeLoadStore loadStore = initDatabase(nameGenerator.getNextName());
        for (Recipe r : recipes) {
            loadStore.saveRecipe(r);
        }

        for (int i = 90; i > 0; i -= 10) {
            Recipe r = recipes.remove(i);
            loadStore.deleteRecipe(r);
        }


        int i = 0;
        RecipeIterator iterator = loadStore.getRecipeIterator();
        for (Recipe actual : iterator) {
            Recipe expect = recipes.get(i);
            assertEquals(expect, actual);
            i++;
        }
        assertEquals(i, recipes.size());

    }


    @Test
    public void testRecipeImportExport() throws IOException {
        ArrayList<Recipe> originalRecipes = buildRecipes(25);
        RecipeLoadStore originalDatabase = initDatabase(nameGenerator.getNextName());
        for (Recipe recipe : originalRecipes)
            originalDatabase.saveRecipe(recipe);

        Path tempDir = makeTempDir();
        File file = Paths.get(tempDir.toString(), "recipe-export.dat").toFile();

        ImportExportManager importExportManager = new ImportExportManager();
        importExportManager.exportRecipes(new FileOutputStream(file), originalDatabase);

        RecipeLoadStore importDatabase = initDatabase(nameGenerator.getNextName());

        importExportManager.importRecipes(new FileInputStream(file), importDatabase);

        RecipeIterator iterator = importDatabase.getRecipeIterator();
        int i = 0;
        for (Recipe actual : iterator) {
            Recipe expect = originalRecipes.get(i++);
            assertEquals(expect, actual);
        }
        assertEquals(i, originalRecipes.size());

    }


    private ArrayList<Recipe> buildRecipes(int count) {
        ArrayList<Recipe> output = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Recipe tmp = fakeRecipeFactory.makeRandomRecipe(5, 10);
            output.add(tmp);
        }
        return output;
    }


    private RecipeLoadStore initDatabase(String name) {
        Context context = ApplicationProvider.getApplicationContext();
        RoomDatabase.Builder<RecipeDatabase> builder = Room.databaseBuilder(context, RecipeDatabase.class, name);
        RecipeLoadStore.initInMemoryInstance(builder);
        return RecipeLoadStore.getInstance();
    }


    @After
    public void clearDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        for (String name : nameGenerator) {
            context.getDatabasePath(name).delete();
        }
    }

    private Path makeTempDir() {
        Path path = null;
        try {
            path = Files.createTempDirectory("test");
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return path;

    }
}
