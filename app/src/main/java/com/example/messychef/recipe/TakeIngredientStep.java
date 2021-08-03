package com.example.messychef.recipe;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.messychef.recipe.dao.IngredientIndex;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "take-ingredient")
public class TakeIngredientStep extends Step implements IngredientList, SetIngredientsFromDB {

    @PrimaryKey
    private int takeIngredientID;


    @Ignore
    private int[] ingredients;

    private String name;

    public TakeIngredientStep(String name) {
        this.name = name;
    }

    @Ignore
    public TakeIngredientStep(String name, int[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public void setTakeIngredientID(int takeIngredientID) {
        this.takeIngredientID = takeIngredientID;
    }

    public int getTakeIngredientID() {
        return takeIngredientID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(int[] ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TakeIngredientStep that = (TakeIngredientStep) o;
        return Arrays.equals(ingredients, that.ingredients) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(ingredients);
        return result;
    }

    @Override
    public String toString() {
        return "TakeIngredientStep{" +
                "ingredients=" + Arrays.toString(ingredients) +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int[] getIngredientIndexList() {
        return ingredients;
    }

    @Override
    public void setIngredientList(int[] list) {
        this.ingredients = list;
    }

    @Override
    public int[] getIngredients() {
        return this.ingredients;
    }

    @Override
    public int getType() {
        return IngredientIndex.TAKE;
    }

    @Override
    public int getId() {
        return takeIngredientID;
    }
}
