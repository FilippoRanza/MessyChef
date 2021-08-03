package com.example.messychef.recipe.load_store;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.SetIngredientsFromDB;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.recipe.dao.IngredientDao;
import com.example.messychef.recipe.dao.IngredientIndex;
import com.example.messychef.recipe.dao.IngredientIndexDao;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;
import com.example.messychef.recipe.dao.RecipeProcessDao;
import com.example.messychef.recipe.dao.RecipeTimerDao;
import com.example.messychef.recipe.dao.TakeIngredientDao;

class RecipeStore {

    private final RecipeDatabase database;

    RecipeStore(RecipeDatabase database) {
        this.database = database;
    }

    void storeRecipe(Recipe recipe) {
        updateRecipeIndex(recipe);
        saveRecipeIngredients(recipe);
        saveRecipeSteps(recipe);
        saveRecipe(recipe);
    }

    void updateRecipe(Recipe recipe) {

    }


    private void updateRecipeIndex(Recipe recipe) {
        RecipeDao dao = database.getRecipeDao();
        int lastID = dao.getLastRecipeID();
        recipe.setRecipeID(lastID + 1);
        recipe.updateComponentID();
        updateStepId(recipe.getSteps());
    }


    private void saveRecipe(Recipe recipe) {
        RecipeDao dao = database.getRecipeDao();
        dao.saveRecipe(recipe);
    }

    private void saveRecipeIngredients(Recipe recipe) {
        IngredientDao dao = database.getIngredientDao();
        for (Ingredient i : recipe.getIngredients()) {
            System.out.println(i.getRecipeID());
            dao.addIngredient(i);
        }
    }


    private void saveRecipeSteps(Recipe recipe) {
        saveSteps(recipe.getSteps());
        saveAllIngredientList(recipe.getSteps());
    }

    private void saveSteps(Step[] steps) {

        TakeIngredientDao takeIngredientDao = database.getTakeIngredientDao();
        RecipeProcessDao recipeProcessDao = database.getRecipeProcessDao();
        RecipeTimerDao recipeTimerDao = database.getRecipeTimerDao();


        for (Step step : steps) {
            if (step instanceof TakeIngredientStep) {
                TakeIngredientStep s = (TakeIngredientStep) step;
                s.setTakeIngredientID(takeIngredientDao.getLastTakeIngredientID() + 1);
                takeIngredientDao.addTakeIngredient(s);
            } else if (step instanceof RecipeProcess) {
                RecipeProcess s = (RecipeProcess) step;
                s.setProcessID(recipeProcessDao.getLastRecipeProcessID() + 1);
                recipeProcessDao.addRecipeProcess((RecipeProcess) step);
            } else if (step instanceof RecipeTimer) {
                recipeTimerDao.addRecipeTimer((RecipeTimer) step);
            }
        }
    }

    private void updateStepId(Step[] steps) {
        TakeIngredientDao takeIngredientDao = database.getTakeIngredientDao();
        RecipeProcessDao recipeProcessDao = database.getRecipeProcessDao();
        for (Step step : steps) {
            if (step instanceof TakeIngredientStep) {
                TakeIngredientStep s = (TakeIngredientStep) step;
                s.setTakeIngredientID(takeIngredientDao.getLastTakeIngredientID() + 1);
            } else if (step instanceof RecipeProcess) {
                RecipeProcess s = (RecipeProcess) step;
                s.setProcessID(recipeProcessDao.getLastRecipeProcessID() + 1);
            }
        }
    }

    private void saveAllIngredientList(Step[] step) {
        for (Step s : step) {
            if (s instanceof SetIngredientsFromDB)
                saveIngredientList((SetIngredientsFromDB) s);
        }
    }

    private void saveIngredientList(SetIngredientsFromDB step) {
        IngredientIndexDao ingredientIndexDao = database.getIngredientIndexDao();
        for (int i : step.getIngredients()) {
            IngredientIndex index = new IngredientIndex(step.getId(), i, step.getType());
            ingredientIndexDao.addIngredientIndex(index);
        }
    }


}
