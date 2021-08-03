package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.messychef.recipe.RecipeTimer;

import java.util.List;

@Dao
public interface RecipeTimerDao {


    @Query("SELECT * FROM `recipe-timer` WHERE recipeID = (:recipeID)")
    List<RecipeTimer> getTimerByRecipe(int recipeID);

    @Insert
    void addRecipeTimer(RecipeTimer timer);

}
