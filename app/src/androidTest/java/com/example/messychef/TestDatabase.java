package com.example.messychef;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;
import com.example.messychef.recipe.load_store.RecipeLoadStore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class TestDatabase {

    static final String READ_WRITE_DB = "read-write";
    static final String MASSIVE_WRITE_DB = "massive-write";

    FakeRecipeFactory fakeRecipeFactory;

    @Before
    public void initialize() {
        fakeRecipeFactory = new FakeRecipeFactory();
    }

    @Test
    public void writeDataInDatabase() {
        Recipe recipe = fakeRecipeFactory.makeRandomRecipe(5, 10);
        RecipeLoadStore loadStore = initDatabase(READ_WRITE_DB);
        loadStore.saveRecipe(recipe);
        Recipe newRecipe = loadStore.loadRecipeById(1);
        assertEquals(recipe, newRecipe);
    }


    @Test
    public void testRecipeNameList() {
        ArrayList<Recipe> recipes = buildRecipes(100);
        RecipeLoadStore loadStore = initDatabase(MASSIVE_WRITE_DB);
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
        context.getDatabasePath(READ_WRITE_DB).delete();
        context.getDatabasePath(MASSIVE_WRITE_DB).delete();
    }




}
