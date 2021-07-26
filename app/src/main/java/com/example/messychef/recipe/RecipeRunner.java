package com.example.messychef.recipe;

public class RecipeRunner {

    private static final int INGREDIENT_STEP = -1;
    private static RecipeRunner instance;

    private Recipe recipe;
    private int step;

    private RecipeRunner() {
    }

    public static RecipeRunner getInstance() {
        if (instance == null) {
            instance = new RecipeRunner();
        }
        return instance;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        step = INGREDIENT_STEP;
    }

    public boolean hasNext() {
        return step < (recipe.getSteps().length);
    }

    public boolean hasPrev() {
        return step > 0;
    }

    public void nextStep() {
        this.step++;
    }

    public void prevStep() {
        step--;
        if (step < 0)
            step = 0;
    }


    public <T extends Step> T getStep() {
        T output = null;
        if(step >= 0) {
            if(step < recipe.getSteps().length)
                output = (T) recipe.getSteps()[step];
            else
                output = (T)  new RecipeLastStep();
        }
        return output;
    }

    public Ingredient[] getIngredients() {
        return recipe.getIngredients();
    }

    public void reset() {
        this.recipe = null;
    }

    public String getRecipeName() {
        return recipe.getName();
    }

    public String[] getIngredientsName() {
        Step step = getStep();
        if (step instanceof IngredientList)
            return makeIngredientList((IngredientList) step);
        throw new IllegalStateException("cannot call getIngredientsName on an item without Ingredients");
    }

    private String[] makeIngredientList(IngredientList list) {
        int[] ingredients = list.getIngredientIndexList();
        String[] output = new String[ingredients.length];
        for (int i = 0; i < ingredients.length; i++) {
            output[i] = recipe.getIngredients()[ingredients[i]].getName();
        }
        return output;
    }

}
