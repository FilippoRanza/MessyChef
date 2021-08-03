package com.example.messychef.recipe.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class IngredientIndex {

    public static final int PROCESS = 0;
    public static final int TAKE = 1;

    @PrimaryKey(autoGenerate = true)
    private int ingredientIndexID;

    private final int stepID;
    private final int ingredientID;
    private final int type;

    public IngredientIndex(int stepID, int ingredientID, int type) {
        this.stepID = stepID;
        this.ingredientID = ingredientID;
        this.type = type;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public int getStepID() {
        return stepID;
    }

    public int getIngredientIndexID() {
        return ingredientIndexID;
    }


    public void setIngredientIndexID(int ingredientIndexID) {
        this.ingredientIndexID = ingredientIndexID;
    }

    public int getType() {
        return type;
    }
}
