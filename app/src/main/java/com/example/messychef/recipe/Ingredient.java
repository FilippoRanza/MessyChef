package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.Objects;

public class Ingredient implements Serializable  {

    private String name;
    private double quantity;
    private String unit;


    public Ingredient(String name, double amount) {
        new Ingredient(name, amount, "");
    }

    public Ingredient(String name, double amount, String unit) {
        this.name = name;
        this.quantity = amount;
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Double.compare(that.quantity, quantity) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, quantity, unit);
    }
}
