package com.example.messychef.recipe;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "ingredients")
public class Ingredient implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ingredientID;

    private int recipeIndex;

    private int recipeID;
    private final String name;
    private final double quantity;
    private final String unit;


    @Ignore
    public Ingredient(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
        this.unit = "";
    }

    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public int getRecipeIndex() {
        return recipeIndex;
    }

    public void setRecipeIndex(int recipeIndex) {
        this.recipeIndex = recipeIndex;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return String.format("%s %.2f%s", name, quantity, unit);
    }

    public String getUnit() {
        return unit;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }


    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(that.quantity, quantity) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, unit);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
