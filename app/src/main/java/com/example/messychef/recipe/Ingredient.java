package com.example.messychef.recipe;

import java.io.Serializable;
import java.util.Objects;

public class Ingredient implements Serializable {

    private final String name;
    private final double quantity;
    private final String unit;


    public Ingredient(String name, double amount) {
        this.name = name;
        this.quantity = amount;
        this.unit = "";
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

    public String getDescription() {
        return String.format("%s %.2f%s", name, quantity, unit);
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

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                '}';
    }
}
