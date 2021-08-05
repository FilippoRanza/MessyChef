package com.example.messychef.recipe.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.TakeIngredientStep;

@Database(entities = {
        Recipe.class, Ingredient.class, RecipeTimer.class,
        RecipeProcess.class, TakeIngredientStep.class, IngredientIndex.class},
        version = 4)
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao getRecipeDao();

    public abstract IngredientDao getIngredientDao();

    public abstract RecipeProcessDao getRecipeProcessDao();

    public abstract RecipeTimerDao getRecipeTimerDao();

    public abstract TakeIngredientDao getTakeIngredientDao();

    public abstract IngredientIndexDao getIngredientIndexDao();


}
