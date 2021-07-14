package com.example.messychef.recipe;

public class RecipeTimer extends Step {

    private static final int NO_STEPS = -1;

    private int globalTime;
    private int stepTime;
    private String name;


    RecipeTimer(String name, int globalTime, Integer stepTime) {
        this.name = name;
        this.globalTime = globalTime;
        if(stepTime == null)
            this.stepTime = NO_STEPS;
        else
            this.stepTime = stepTime;
    }


    @Override
    public String getName() {
        return name;
    }
}
