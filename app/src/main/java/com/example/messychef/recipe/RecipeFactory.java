package com.example.messychef.recipe;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RecipeFactory {

    private static final int NO_MODIFY = -1;

    private static RecipeFactory instance;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int modifyIngredientId;
    private int modifyStepId;

    private RecipeFactory() {
        name = null;
        ingredients = null;
        steps = null;

        modifyIngredientId = -1;
        modifyStepId = -1;


    }

    public static RecipeFactory getInstance() {
        if (instance == null) {
            instance = new RecipeFactory();
        }
        return instance;
    }

    public void initFactory() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        name = null;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void deleteIngredient(int index) {
        ingredients.remove(index);
    }

    public void deleteStep(int index) {
        steps.remove(index);
    }

    public Recipe getRecipe() {

        Ingredient[] ingredients =  this.ingredients.toArray(new Ingredient[]{});
        Step[] steps = this.steps.toArray(new Step[]{});

        Recipe output = new Recipe(name, ingredients, steps);
        this.ingredients = null;
        this.steps = null;
        this.name = null;

        return output;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public Stream<Ingredient> streamIngredients() {
        return ingredients.stream();
    }

    public Stream<Step> streamSteps() {
        return steps.stream();
    }

    public void setModifyIngredientId(int modifyIngredientId) {
        this.modifyIngredientId = modifyIngredientId;
    }

    public void commitIngredient(Ingredient i) {
        ingredients.set(modifyIngredientId, i);
        modifyIngredientId = -1;
    }

    public Ingredient getModifyIngredient() {
        return ingredients.get(modifyIngredientId);
    }

    public void setModifyStepId(int modifyStepId) {
        this.modifyStepId = modifyStepId;
    }

    public void commitStep() {
        modifyStepId = -1;
    }

    public Step getModifyStep() {
        return steps.get(modifyStepId);
    }

}
