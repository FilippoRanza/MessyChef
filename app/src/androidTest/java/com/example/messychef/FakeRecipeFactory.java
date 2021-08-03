package com.example.messychef;

import com.example.messychef.recipe.Ingredient;
import com.example.messychef.recipe.Recipe;
import com.example.messychef.recipe.RecipeProcess;
import com.example.messychef.recipe.RecipeTimer;
import com.example.messychef.recipe.Step;
import com.example.messychef.recipe.TakeIngredientStep;

import java.util.Random;

public class FakeRecipeFactory {

    private final Random random;

    FakeRecipeFactory() {
        random = new Random();
    }

    public Recipe makeRandomRecipe(int ingredientCount, int stepCount) {
        Ingredient[] ingredients = makeIngredientList(ingredientCount);
        Step[] steps = makeStepList(stepCount);
        String name = makeRandomString(10, 25);
        return new Recipe(name, ingredients, steps);
    }

    private Ingredient[] makeIngredientList(int len) {
        Ingredient[] output = new Ingredient[len];
        for (int i = 0; i < len; i++) {
            output[i] = makeRandomIngredient();
        }
        return output;
    }

    private Step[] makeStepList(int len) {
        Step[] output = new Step[len];
        for (int i = 0; i < len; i++) {
            output[i] = makeRandomStep();
        }
        return output;
    }

    private Step makeRandomStep() {
        int type = getRandomInt() % 3;
        Step step = null;
        switch (type) {
            case 0:
                step = makeRandomRecipeProcess();
                break;
            case 1:
                step = makeRandomRecipeTimer();
                break;
            case 2:
                step = makeRandomTakeIngredient();
                break;
        }
        return step;
    }

    private TakeIngredientStep makeRandomTakeIngredient() {
        int[] ingredients = makeRandomIntArray(getRandomInt() % 100);
        String name = makeRandomString(5, 15);
        return new TakeIngredientStep(name, ingredients);
    }

    private RecipeTimer makeRandomRecipeTimer() {
        int globalTime = getRandomInt() % 180;
        int stepTime = getRandomInt() % 45;
        String name = makeRandomString(10, 40);
        return new RecipeTimer(name, globalTime, stepTime);
    }

    private RecipeProcess makeRandomRecipeProcess() {
        int[] ingredients = makeRandomIntArray(getRandomInt() % 100);
        String name = makeRandomString(5, 15);
        String description = makeRandomString(100, 500);
        return new RecipeProcess(name, description, ingredients);
    }


    private Ingredient makeRandomIngredient() {
        String name = makeRandomString(3, 100);
        String unit = makeRandomString(1, 4);
        double amount = random.nextDouble() * 100;
        return new Ingredient(name, amount, unit);
    }

    private String makeRandomString(int min, int max) {
        int len = randIntInRange(min, max);
        StringBuilder builder = new StringBuilder();
        for (; len > 0; len--) {
            builder.append(randChar());
        }
        return builder.toString();
    }

    private int randIntInRange(int a, int b) {
        int delta = b - a;
        int tmp = getRandomInt() % delta;
        return tmp + a;
    }

    private char randChar() {
        int asciiA = 'A';
        int asciiZ = 'Z';
        int delta = asciiZ - asciiA;
        int tmp = getRandomInt() % delta;
        tmp += asciiA;
        return (char) tmp;
    }

    private int[] makeRandomIntArray(int size) {
        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            output[i] = getRandomInt();
        }
        return output;
    }

    private int getRandomInt() {
        int tmp = random.nextInt();
        return (tmp < 0) ? -tmp : tmp;
    }

}
