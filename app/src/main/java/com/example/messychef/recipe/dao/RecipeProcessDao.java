package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.messychef.recipe.RecipeProcess;

import java.util.List;

@Dao
public interface RecipeProcessDao {


    @Query("SELECT * FROM `recipe-process` WHERE recipeID = (:recipeID)")
    List<RecipeProcess> getRecipeProcessByRecipe(int recipeID);

    @Query("SELECT MAX(processID) FROM `recipe-process`")
    int getLastRecipeProcessID();

    @Insert
    void addRecipeProcess(RecipeProcess process);


}
