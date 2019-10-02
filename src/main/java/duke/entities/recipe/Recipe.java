package duke.entities.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Recipe {


    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private double cost;
    private int difficultyLevel;
    private int time;

    public Recipe(@JsonProperty String name) {
        this.name = name;
    }

    public Recipe() {

    }
    public Recipe init() {
        name = "Cheese Cake";
        List<Step> stepList = new ArrayList<>();
        Step step1 = new Step("");
        step1.init();
        stepList.add(step1);
        this.steps = stepList;
        ingredients.add(new Ingredient("cream"));
        ingredients.add(new Ingredient("cheese"));
        cost = 20.22;
        difficultyLevel = 5;
        time = 40;
        return this;
    }


    public void setName(String name) {
        this.name = name;
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

    public int getDifficultyLevel() {
        return difficultyLevel;
    }
    public double getCost() {
        return cost;
    }
    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }
    public List<Step> getSteps() {
        return this.steps;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addStep(Step step) {
        steps.add(step);
    }
}
