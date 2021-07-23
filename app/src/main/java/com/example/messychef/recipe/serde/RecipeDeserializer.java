package com.example.messychef.recipe.serde;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;

import java.io.DataInputStream;
import java.io.IOException;

public class RecipeDeserializer {

    private final DataInputStream reader;

    public RecipeDeserializer(DataInputStream reader) {
        this.reader = reader;
    }

    public Recipe deserialize() throws IOException {
        String name = reader.readUTF();
        Ingredient[] ingredients = deserializeIngredients();
        Step[] steps = deserializeSteps();
        return new Recipe(name, ingredients, steps);
    }

    private Ingredient[] deserializeIngredients() throws IOException {
        int len = reader.readInt();
        Ingredient[] output = new Ingredient[len];
        for (int i = 0; i < len; i++) {
            output[i] = deserializeIngredient();
        }

        return output;
    }

    private Ingredient deserializeIngredient() throws IOException {
        String name = reader.readUTF();
        double amount = reader.readDouble();
        String unit = reader.readUTF();

        return new Ingredient(name, amount, unit);
    }


    private Step[] deserializeSteps() throws IOException {
        int len = reader.readInt();
        Step[] steps = new Step[len];
        for (int i = 0; i < len; i++) {
            steps[i] = deserializeStep();
        }

        return steps;
    }

    private Step deserializeStep() throws IOException {
        int type = reader.readInt();
        Step step = null;
        switch (type) {
            case Constants.STEP_PROCESS:
                step = deserializeRecipeProcess();
                break;
            case Constants.STEP_TAKE:
                step = deserializeTakeIngredient();
                break;
            case Constants.STEP_TIMER:
                step = deserializeRecipeTimer();
                break;
        }

        return step;
    }

    private RecipeTimer deserializeRecipeTimer() throws IOException {
        String name = reader.readUTF();
        int global = reader.readInt();
        int step = reader.readInt();
        return new RecipeTimer(name, global, step);
    }

    private TakeIngredientStep deserializeTakeIngredient() throws IOException {
        String name = reader.readUTF();
        int[] ingredients = deserializeIntArray();
        return new TakeIngredientStep(name, ingredients);
    }

    private RecipeProcess deserializeRecipeProcess() throws IOException {
        String name = reader.readUTF();
        String description = reader.readUTF();
        int[] ingredients = deserializeIntArray();
        return new RecipeProcess(name, description, ingredients);
    }

    private int[] deserializeIntArray() throws IOException {
        int len = reader.readInt();
        int[] output = new int[len];
        for (int i = 0; i < len; i++) {
            output[i] = reader.readInt();
        }

        return output;
    }

}
