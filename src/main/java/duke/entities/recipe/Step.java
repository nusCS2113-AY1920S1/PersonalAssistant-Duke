package duke.entities.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Step {
    private String description;
    private List<Ingredient> ingredients;
    private int time; //in minutes

    public Step (@JsonProperty String description) {
        this.description = description;
        ingredients = parseIngredient(description);
        time = parseTime(description);
    }

    public ArrayList<Ingredient> parseIngredient(String description) {
        //Assume able to return a list of integer.
        return new ArrayList<Ingredient>();
    }

    public int parseTime(String description) {
        return 1;
    }

    public Step init () {
        Ingredient ingt1 = new Ingredient("cream");
        Ingredient ingt2 = new Ingredient("cheese");
        ArrayList<Ingredient> ingts = new ArrayList<>();
        ingts.add(ingt1);
        ingts.add(ingt2);
        description = "add cream to cheese";
        ingredients = ingts;
        time = 30;
        return this;
    }

    public String getDescription() {
        return description;
    }
    public int getTime() {
        return time;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

}
