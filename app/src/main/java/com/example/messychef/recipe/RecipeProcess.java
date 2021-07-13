package com.example.messychef.recipe;

import java.util.ArrayList;

public class RecipeProcess extends Step {

    private String description;
    private ArrayList<Ingredient> ingredients;

    private String name;

    RecipeProcess(String name, String description, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }



    @Override
    public String getName() {
        return name;
    }


}
