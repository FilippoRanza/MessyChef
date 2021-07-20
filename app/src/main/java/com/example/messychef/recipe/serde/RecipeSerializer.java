package com.example.messychef.recipe.serde;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;

import java.io.DataOutputStream;
import java.io.IOException;

public class RecipeSerializer {

    private final DataOutputStream writer;

    public RecipeSerializer(DataOutputStream writer) {
        this.writer = writer;
    }

    public void serialize(Recipe r) throws IOException {
        writer.writeUTF(r.getName());
        serializeIngredients(r.getIngredients());
        serializeSteps(r.getSteps());
    }

    private void serializeIngredients(Ingredient[] ingredients) throws IOException{
        int count = ingredients.length;
        writer.writeInt(count);
        for(Ingredient i: ingredients) {
            serializeIngredient(i);
        }
    }

    private void serializeIngredient(Ingredient ingredient) throws IOException {
        writer.writeUTF(ingredient.getName());
        writer.writeDouble(ingredient.getQuantity());
        writer.writeUTF(ingredient.getUnit());
    }

    private void serializeSteps(Step[] steps) throws IOException {
        int count = steps.length;
        writer.writeInt(count);
        for(Step s: steps) {
            serializeStep(s);
        }
    }

    private void serializeStep(Step step) throws IOException {
        if(step instanceof TakeIngredientStep) {
            writer.writeInt(Constants.STEP_TAKE);
            serializeTakeIngredient((TakeIngredientStep) step);
        }
        else if (step instanceof RecipeTimer) {
            writer.writeInt(Constants.STEP_TIMER);
            serializeRecipeTimer((RecipeTimer) step);
        }
        else if (step instanceof RecipeProcess) {
            writer.writeInt(Constants.STEP_PROCESS);
            serializeRecipeProcess((RecipeProcess) step);
        }
    }

    private void serializeTakeIngredient(TakeIngredientStep step) throws IOException {
        writer.writeUTF(step.getName());
        int[] ingredients = step.getIngredients();
        serializeIntArray(ingredients);
    }

    private void serializeRecipeTimer(RecipeTimer timer) throws IOException {
        writer.writeUTF(timer.getName());
        writer.writeInt(timer.getGlobalTime());
        writer.writeInt(timer.getStepTime());
    }

    private void serializeRecipeProcess(RecipeProcess process) throws IOException {
        writer.writeUTF(process.getName());
        writer.writeUTF(process.getDescription());
        serializeIntArray(process.getIngredients());
    }



    private void serializeIntArray(int[] ints) throws IOException {
        writer.writeInt(ints.length);
        for(int i : ints) {
            writer.writeInt(i);
        }
    }
}
