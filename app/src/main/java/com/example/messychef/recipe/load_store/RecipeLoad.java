package com.example.messychef.recipe.load_store;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.SetIngredientsFromDB;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;
import com.example.messychef.recipe.dao.RecipeDao;
import com.example.messychef.recipe.dao.RecipeDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class RecipeLoad {
    private final RecipeDatabase database;

    RecipeLoad(RecipeDatabase database) {
        this.database = database;
    }

    Recipe loadRecipe(int id) {
        RecipeDao recipeDao = database.getRecipeDao();
        Recipe recipe = recipeDao.getRecipeByID(id);

        recipe.setIngredients(getIngredients(id));
        recipe.setSteps(getSteps(id));

        return recipe;
    }

    private Step[] getSteps(int id) {
        List<RecipeProcess> processes = database.getRecipeProcessDao().getRecipeProcessByRecipe(id);
        processes.forEach(this::setIngredientList);
        List<RecipeTimer> timers = database.getRecipeTimerDao().getTimerByRecipe(id);
        List<TakeIngredientStep> takeIngredientSteps = database.getTakeIngredientDao().getTakeIngredientByRecipe(id);
        takeIngredientSteps.forEach(this::setIngredientList);

        int total = processes.size() + timers.size() + takeIngredientSteps.size();

        ArrayList<Step> steps = new ArrayList<>(total);
        steps.addAll(processes);
        steps.addAll(timers);
        steps.addAll(takeIngredientSteps);

        steps.sort(Comparator.comparing(Step::getRecipeIndex));


        return steps.toArray(new Step[0]);
    }

    private Ingredient[] getIngredients(int id) {
        List<Ingredient> ingredients = database.getIngredientDao().getIngredientByRecipe(id);
        ingredients.sort(Comparator.comparing(Ingredient::getRecipeIndex));


        return ingredients.toArray(new Ingredient[0]);
    }


    private void setIngredientList(SetIngredientsFromDB step) {
        int id = step.getId();
        int type = step.getType();
        List<Integer> ingredientsList = database.getIngredientIndexDao().getIngredientsById(id, type);
        int[] ingredients = new int[ingredientsList.size()];
        for (int i = 0; i < ingredients.length; i++)
            ingredients[i] = ingredientsList.get(i);

        step.setIngredientList(ingredients);
    }
}
