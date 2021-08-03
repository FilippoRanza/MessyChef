package com.example.messychef.recipe;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "recipe-timer")
public class RecipeTimer extends Step implements Serializable {

    private static final int NO_STEPS = -1;

    @PrimaryKey(autoGenerate = true)
    private int timerID;

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


    public int getTimerID() {
        return timerID;
    }

    public void setTimerID(int timerID) {
        this.timerID = timerID;
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
