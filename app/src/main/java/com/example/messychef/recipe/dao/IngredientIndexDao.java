package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientIndexDao {

    @Insert
    void addIngredientIndex(IngredientIndex ingredientIndex);

    @Query("SELECT ingredientID FROM ingredientindex WHERE stepID = (:id) and type = (:type)")
    List<Integer> getIngredientsById(int id, int type);

}
