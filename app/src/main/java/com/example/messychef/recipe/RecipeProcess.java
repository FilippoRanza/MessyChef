package com.example.messychef.recipe;

import java.util.ArrayList;

public class RecipeProcess extends Step {

    private String description;
    private ArrayList<Ingredient> ingredients;

    private String name;


    @Override
    public String getName() {
        return name;
    }


}
