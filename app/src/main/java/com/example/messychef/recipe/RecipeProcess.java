package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeProcess extends Step implements Serializable {

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
