package com.example.messychef.recipe;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String name;
    private int amount;


    public Ingredient(String name, int amount) {
        this.amount = amount;
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

}
