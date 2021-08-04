package com.example.messychef.recipe.load_store;

import androidx.annotation.NonNull;

import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;

import java.util.Iterator;

public class RecipeIterator implements Iterator<Recipe>, Iterable<Recipe> {

    private final int count;
    private int current;

    private final RecipeDatabase database;
    private final RecipeLoad load;
    private final RecipeDao dao;

    RecipeIterator(RecipeDatabase database) {
        this.database = database;
        load = new RecipeLoad(database);
        dao = database.getRecipeDao();
        count = dao.getLastRecipeID();
        current = 0;
    }


    public int getRecipeCount() {
        return dao.getRecipeCount();
    }

    @Override
    public boolean hasNext() {
        while (!existsRecipe() && current <= count)
            current++;

        if (current <= count) {
            return true;
        } else {
            database.close();
            return false;
        }
    }

    @Override
    public Recipe next() {
        return load.loadRecipe(current++);
    }


    private boolean existsRecipe() {
        return dao.findRecipe(current) > 0;
    }

    @NonNull
    @Override
    public Iterator<Recipe> iterator() {
        return this;
    }
}
