package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.messychef.recipe.RecipeTimer;

import java.util.List;

@Dao
public interface RecipeTimerDao {


    @Query("SELECT * FROM `recipe-timer` WHERE recipeID = (:recipeID)")
    List<RecipeTimer> getTimerByRecipe(int recipeID);

    @Insert
    void addRecipeTimer(RecipeTimer timer);

    @Delete
    void delete(RecipeTimer timer);

    @Update
    void update(RecipeTimer timer);

}
