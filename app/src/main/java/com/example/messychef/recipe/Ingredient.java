package com.example.messychef.recipe;

import java.io.Serializable;

public class Ingredient implements Serializable  {

    private String name;
    private double quantity;


    public Ingredient(String name, double amount) {
        this.quantity = amount;
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

}
