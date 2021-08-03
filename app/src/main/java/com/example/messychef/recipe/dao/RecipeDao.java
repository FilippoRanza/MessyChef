package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.messychef.recipe.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    class RecipeInfo {
        private String name;
        private int recipeID;

        public RecipeInfo() {
        }

        public int getRecipeID() {
            return recipeID;
        }

        public String getName() {
            return name;
        }

        public void setRecipeID(int recipeID) {
            this.recipeID = recipeID;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    @Query("SELECT * FROM recipes where recipeID = (:recipeID)")
    Recipe getRecipeByID(int recipeID);

    @Query("SELECT name, recipeID FROM recipes")
    List<RecipeInfo> getRecipesName();

    @Query("SELECT MAX(recipeID) FROM recipes ")
    int getLastRecipeID();

    @Insert
    void saveRecipe(Recipe r);

    @Delete
    void delete(Recipe r);

}
