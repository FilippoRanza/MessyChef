package com.example.messychef;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;
import com.example.messychef.recipe.load_store.RecipeLoadStore;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class TestDatabase {

    FakeRecipeFactory fakeRecipeFactory;

    @Before
    public void initialize() {
        fakeRecipeFactory = new FakeRecipeFactory();
    }

    @Test
    public void writeDataInDatabase() {
        Recipe recipe = fakeRecipeFactory.makeRandomRecipe(5, 10);
        RecipeLoadStore loadStore = initInMemoryDatabase();
        loadStore.saveRecipe(recipe);
        Recipe newRecipe = loadStore.loadRecipeById(1);
        assertEquals(recipe, newRecipe);
    }


    @Test
    public void testRecipeNameList() {
        ArrayList<Recipe> recipes = buildRecipes(100);
        RecipeLoadStore loadStore = initInMemoryDatabase();
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



    private RecipeLoadStore initInMemoryDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        RecipeDatabase database = Room.inMemoryDatabaseBuilder(context, RecipeDatabase.class).build();
        RecipeLoadStore.initInMemoryInstance(database);
        return RecipeLoadStore.getInstance();
    }




}
