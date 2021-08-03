package com.example.messychef.recipe.load_store;

import android.content.Context;

import androidx.room.Room;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;

import java.util.List;

public class RecipeLoadStore {


    private final RecipeLoad recipeLoad;
    private final RecipeStore recipeStore;
    private final RecipeDatabase database;

    private static RecipeLoadStore instance;

    private RecipeLoadStore(Context owner) {
        database = Room.databaseBuilder(owner, RecipeDatabase.class, "recipe-database").build();
        recipeLoad = new RecipeLoad(database);
        recipeStore = new RecipeStore(database);
    }

    private RecipeLoadStore(RecipeDatabase database) {
        this.database = database;
        recipeStore = new RecipeStore(database);
        recipeLoad = new RecipeLoad(database);
    }

    public static void initInstance(Context owner) {
        System.out.println("Initialize DB Connection");
        instance = new RecipeLoadStore(owner);
        System.out.println("DB Connection Ready");
    }

    public static void initInMemoryInstance(RecipeDatabase database) {
        instance = new RecipeLoadStore(database);
    }

    public static RecipeLoadStore getInstance() {
        if (instance == null)
            throw new IllegalStateException();
        return instance;
    }

    public Recipe loadRecipeById(int id) {
        return recipeLoad.loadRecipe(id);
    }

    public void saveRecipe(Recipe r) {
        recipeStore.storeRecipe(r);
    }

    public List<RecipeDao.RecipeInfo> getRecipeList() {
        RecipeDao dao = database.getRecipeDao();
        return dao.getRecipesName();
    }

}
