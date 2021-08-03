package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.messychef.recipe.TakeIngredientStep;

import java.util.List;

@Dao
public interface TakeIngredientDao {

    @Query("SELECT * FROM `take-ingredient` WHERE recipeID = (:recipeID)")
    List<TakeIngredientStep> getTakeIngredientByRecipe(int recipeID);

    @Query("SELECT MAX(takeIngredientID) FROM `TAKE-INGREDIENT`")
    int getLastTakeIngredientID();

    @Insert
    void addTakeIngredient(TakeIngredientStep step);

    @Delete
    void delete(TakeIngredientStep step);

    @Update
    void update(TakeIngredientStep step);

}
