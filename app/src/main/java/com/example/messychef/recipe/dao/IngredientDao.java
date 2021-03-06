package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.messychef.recipe.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Query("SELECT * FROM ingredients WHERE recipeID = (:recipeId)")
    List<Ingredient> getIngredientByRecipe(int recipeId);

    @Insert
    void addIngredient(Ingredient i);

    @Delete
    void delete(Ingredient i);

    @Update
    void update(Ingredient i);

    @Query("SELECT name FROM ingredients WHERE name like (:name)")
    List<String> searchIngredientByName(String name);

}
