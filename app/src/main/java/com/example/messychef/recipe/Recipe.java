package com.example.messychef.recipe;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "recipes")
public class Recipe {

    public static final int RECIPE_VERY_SIMPLE = 0;
    public static final int RECIPE_SIMPLE = 1;
    public static final int RECIPE_MEDIUM = 2;
    public static final int RECIPE_HARD = 3;
    public static final int RECIPE_VERY_HARD = 4;


    @PrimaryKey
    private int recipeID;
    private String name;

    private int recipeComplexity;
    private int recipeDuration;

    @Ignore
    private Ingredient[] ingredients;

    @Ignore
    private Step[] steps;

    public Recipe() {
        name = null;
        ingredients = null;
        steps = null;
    }

    public Recipe(String name, Ingredient[] ingredients, Step[] steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }


    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int rid) {
        this.recipeID = rid;
    }


    public int getRecipeComplexity() {
        return recipeComplexity;
    }

    public void setRecipeComplexity(int recipeComplexity) {
        this.recipeComplexity = recipeComplexity;
    }


    public void setRecipeDuration(int recipeDuration) {
        this.recipeDuration = recipeDuration;
    }

    public int getRecipeDuration() {
        return recipeDuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) &&
                Arrays.equals(ingredients, recipe.ingredients) &&
                Arrays.equals(steps, recipe.steps);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(ingredients);
        result = 31 * result + Arrays.hashCode(steps);
        return result;
    }

    public void updateComponentID() {
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i].setRecipeID(recipeID);
            ingredients[i].setRecipeIndex(i);
        }
        for (int i = 0; i < steps.length; i++) {
            steps[i].setRecipeIndex(i);
            steps[i].setRecipeID(recipeID);
        }
    }
}
