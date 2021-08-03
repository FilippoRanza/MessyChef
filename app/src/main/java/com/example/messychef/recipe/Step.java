package com.example.messychef.recipe;


import java.io.Serializable;

abstract public class Step implements Serializable {

    private int recipeID;
    private int recipeIndex;

    public abstract String getName();


    public final void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public final int getRecipeID() {
        return recipeID;
    }

    public int getRecipeIndex() {
        return recipeIndex;
    }

    public void setRecipeIndex(int recipeIndex) {
        this.recipeIndex = recipeIndex;
    }
}
