package duke.model.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private String name;
    private String baseUnit;
    private double unitCost;
    private double cost;

    public Ingredient(@JsonProperty("name") String name) {
        this.name = name;
    }

    public Ingredient() {

    }

    public Ingredient(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Ingredient createNewIngredient(String name) {
        this.name = name;
        this.cost = cost;
        return this;
    }

    public void init() {
        name = "cheese";
        cost = 22.02;
        //setPicture("cheese");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public double getCost() {
//        return cost;
//    }
//
//    public void setCost(double cost) {
//        this.cost = cost;
//    }
}
