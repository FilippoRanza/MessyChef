package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.Objects;

public class RecipeTimer extends Step implements Serializable {

    private static final int NO_STEPS = -1;

    private int globalTime;
    private int stepTime;
    private String name;


    public RecipeTimer(String name, int globalTime, Integer stepTime) {
        this.name = name;
        this.globalTime = globalTime;
        if (stepTime == null)
            this.stepTime = NO_STEPS;
        else
            this.stepTime = stepTime;
    }


    @Override
    public String getName() {
        return name;
    }

    public int getGlobalTime() {
        return globalTime;
    }

    public int getStepTime() {
        return stepTime;
    }

    public boolean hasStepTime() {
        return stepTime != NO_STEPS;
    }


    public void setGlobalTime(int globalTime) {
        this.globalTime = globalTime;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setStepTime(Integer stepTime) {
        if (stepTime == null)
            this.stepTime = NO_STEPS;
        else
            this.stepTime = stepTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeTimer that = (RecipeTimer) o;
        return globalTime == that.globalTime &&
                stepTime == that.stepTime &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(globalTime, stepTime, name);
    }

    @Override
    public String toString() {
        return "RecipeTimer{" +
                "globalTime=" + globalTime +
                ", stepTime=" + stepTime +
                ", name='" + name + '\'' +
                '}';
    }
}
