package com.example.messychef.recipe;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.messychef.recipe.dao.IngredientIndex;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "recipe-process")
public class RecipeProcess extends Step implements IngredientList, SetIngredientsFromDB {

    @PrimaryKey
    private int processID;

    private String description;

    @Ignore
    private int[] ingredients;

    private String name;

    public RecipeProcess(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Ignore
    public RecipeProcess(String name, String description, int[] ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(int[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }


    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeProcess that = (RecipeProcess) o;
        return Objects.equals(description, that.description) &&
                Arrays.equals(ingredients, that.ingredients) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(description, name);
        result = 31 * result + Arrays.hashCode(ingredients);
        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "RecipeProcess{" +
                "description='" + description + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
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
        return ingredients;
    }

    @Override
    public int getType() {
        return IngredientIndex.PROCESS;
    }

    @Override
    public int getId() {
        return processID;
    }
}
