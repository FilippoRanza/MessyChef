package com.example.messychef.recipe.load_store;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.SetIngredientsFromDB;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.recipe.dao.IngredientDao;
import com.example.messychef.recipe.dao.IngredientIndexDao;
import com.example.messychef.recipe.dao.RecipeDatabase;

class RecipeDelete {

    RecipeDatabase database;

    RecipeDelete(RecipeDatabase database) {
        this.database = database;
    }


    void deleteRecipe(Recipe recipe) {
        deleteSteps(recipe.getSteps());
        deleteIngredients(recipe.getIngredients());
        database.getRecipeDao().delete(recipe);
    }


    private void deleteSteps(Step[] steps) {
        for (Step s : steps) {
            if (s instanceof SetIngredientsFromDB)
                deleteIngredientIndex((SetIngredientsFromDB) s);
            deleteStep(s);
        }
    }

    private void deleteStep(Step step) {
        if (step instanceof RecipeTimer)
            database.getRecipeTimerDao().delete((RecipeTimer) step);
        else if (step instanceof RecipeProcess)
            database.getRecipeProcessDao().delete((RecipeProcess) step);
        else if (step instanceof TakeIngredientStep)
            database.getTakeIngredientDao().delete((TakeIngredientStep) step);
    }

    private void deleteIngredientIndex(SetIngredientsFromDB step) {
        IngredientIndexDao dao = database.getIngredientIndexDao();
        dao.deleteAll(step.getId(), step.getType());
    }


    private void deleteIngredients(Ingredient[] ingredients) {
        IngredientDao dao = database.getIngredientDao();
        for (Ingredient ingredient : ingredients) {
            dao.delete(ingredient);
        }
    }


}
