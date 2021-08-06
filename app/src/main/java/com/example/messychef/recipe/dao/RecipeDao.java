package com.example.messychef.recipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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


    @Query("SELECT DISTINCT name, recipeID FROM recipes WHERE recipeComplexity <= (:complexity)")
    List<RecipeInfo> searchByComplexity(int complexity);


    @Query("SELECT COUNT(*) FROM recipes")
    int getRecipeCount();

    @Query("SELECT * FROM recipes where recipeID = (:recipeID)")
    Recipe getRecipeByID(int recipeID);

    @Query("SELECT DISTINCT recipes.name, recipes.recipeID FROM RECIPES LEFT JOIN ingredients on recipes.recipeID = ingredients.recipeID WHERE ingredients.name like (:ingredient)")
    List<RecipeInfo> searchByIngredient(String ingredient);

    @Query("SELECT recipes.name, recipes.recipeID FROM RECIPES WHERE name like (:ingredient)")
    List<RecipeInfo> searchByName(String ingredient);


    @Query("SELECT name, recipeID FROM recipes")
    List<RecipeInfo> getRecipesName();

    @Query("SELECT COUNT(*) FROM recipes where recipeID = (:recipeID)")
    int findRecipe(int recipeID);

    @Query("SELECT MAX(recipeID) FROM recipes ")
    int getLastRecipeID();

    @Insert
    void saveRecipe(Recipe r);

    @Delete
    void delete(Recipe r);

    @Update
    void update(Recipe update);

}
