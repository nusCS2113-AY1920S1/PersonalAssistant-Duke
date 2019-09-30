package duke.entities.recipe;

import duke.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private double cost;
    private int diffLevel;
    private int time;

    public Recipe(String name) {
        this.name = name;
    }

    public Recipe init() {
        name = "Cheese Cake";
        List<Step> stepList = new ArrayList<>();
        Step step1 = new Step("");
        step1.init();
        stepList.add(step1);
        this.steps = stepList;
        ingredients = step1.getIngredients();
        cost = 20.22;
        diffLevel = 5;
        time = 40;
        return this;
    }
    public String getName() {
        return name;
    }

    public int getTime() {
        int time = 0;
        for (Step step: steps) {
            time += step.getTime();
        }
        return time;
    }

    public int getDiffLevel() {
        return diffLevel;
    }
    public double getCost() {
        return 20.21;
    }
    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }
    public List<Step> getSteps() {
        return this.steps;
    }
}
