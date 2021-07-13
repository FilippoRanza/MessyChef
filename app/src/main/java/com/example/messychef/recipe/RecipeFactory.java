package com.example.messychef.recipe;

import android.widget.ListView;

import com.example.messychef.utils.IndexValue;
import com.example.messychef.utils.SelectedIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class RecipeFactory {

    private static final int NO_MODIFY = -1;

    private static RecipeFactory instance;
    private String name;
    private ArrayList<IngredientInfo> ingredients;
    private ArrayList<Step> steps;
    private int modifyIngredientId;
    private int modifyStepId;

    private static class IngredientInfo {
        private Ingredient ingredient;
        private boolean inUse;

        IngredientInfo(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        void setUsed() {
            inUse = true;
        }

        void updateIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
        }

        boolean isAvailable() {
            return !inUse;
        }

        Ingredient getIngredient() {
            return ingredient;
        }


        public boolean isTaken() {
            return inUse;
        }
    }


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
        IngredientInfo info = new IngredientInfo(ingredient);
        ingredients.add(info);
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

        Ingredient[] ingredients = this.ingredients
                .stream()
                .map(IngredientInfo::getIngredient)
                .toArray(Ingredient[]::new);
        Step[] steps = this.steps.toArray(new Step[]{});

        Recipe output = new Recipe(name, ingredients, steps);
        this.ingredients = null;
        this.steps = null;
        this.name = null;

        return output;
    }


    public Stream<Ingredient> streamIngredients() {
        return ingredients.stream().map(IngredientInfo::getIngredient);
    }

    public ArrayList<IndexValue<String>> streamAvailableIngredients() {
        ArrayList<IndexValue<String>> output = new ArrayList<>();
        for(int i = 0; i < ingredients.size(); i++) {
            IngredientInfo info = ingredients.get(i);
            if(info.isAvailable()) {
                IndexValue<String> tmp = new IndexValue<>(info.getIngredient().getName(), i);
                output.add(tmp);
            }
        }
        return output;
    }

    public ArrayList<IndexValue<String>> streamTakenIngredients() {
        ArrayList<IndexValue<String>> output = new ArrayList<>();
        for(int i = 0; i < ingredients.size(); i++) {
            IngredientInfo info = ingredients.get(i);
            if(info.isTaken()) {
                IndexValue<String> tmp = new IndexValue<>(info.getIngredient().getName(), i);
                output.add(tmp);
            }
        }
        return output;
    }


    public Stream<Step> streamSteps() {
        return steps.stream();
    }

    public void setModifyIngredientId(int modifyIngredientId) {
        this.modifyIngredientId = modifyIngredientId;
    }

    public void commitIngredient(Ingredient i) {
        IngredientInfo ingredientInfo = ingredients.get(modifyIngredientId);
        ingredientInfo.updateIngredient(i);
        modifyIngredientId = -1;
    }

    public Ingredient getModifyIngredient() {
        IngredientInfo ingredientInfo = ingredients.get(modifyIngredientId);
        return ingredientInfo.getIngredient();
    }

    public void deleteModifyIngredient() {
        if (modifyIngredientId != -1) {
            this.ingredients.remove(modifyIngredientId);
        }
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

    public void addTakeIngredientStep(String name, List<SelectedIndex> selected) {
        ArrayList<Ingredient> taken = getSelectedIngredients(selected);
        TakeIngredientStep step = new TakeIngredientStep(name, taken);
        this.steps.add(step);

    }


    public void addProcessStep(String name, String description, List<SelectedIndex> selected) {
        ArrayList<Ingredient> taken = getSelectedIngredients(selected);
        RecipeProcess process = new RecipeProcess(name, description, taken);
        this.steps.add(process);
    }


    private ArrayList<Ingredient> getSelectedIngredients(List<SelectedIndex> selected) {
        ArrayList<Ingredient> taken = new ArrayList<>();
        for(SelectedIndex index: selected) {
            if(index.isSelected()) {
                IngredientInfo info = ingredients.get(index.getValue());
                info.setUsed();
                taken.add(info.getIngredient());
            }
        }

        return taken;
    }

}
