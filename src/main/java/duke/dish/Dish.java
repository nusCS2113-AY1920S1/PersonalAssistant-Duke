package duke.dish;

import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;

import java.util.ArrayList;
import java.util.List;

public class Dish {

    private String dishname;
    private int total = 0;
    private float rating = 0;
    private IngredientsList ingredientsList;

    public Dish(String name) {
        this.dishname = name;
        this.ingredientsList = new IngredientsList();
    }

    public int getTotalNumberOfOrders() {
        return total;
    }

    public void setNumberOfOrders(int amount) {
        total = total + amount;
    }

    public void clearOrders() {
        total = 0;
    }

    public String getDishname() {
        return dishname;
    }

    public void setRating(int r) {
        rating = (total * rating + r) / total;
    }

    public float getRating() {
        return rating;
    }

    public void addIngredients(Ingredient ingredients) {
        ingredientsList.addEntry(ingredients);
    }

    public String toString() {
        String str = "Recipe for: " + dishname;
        for (Ingredient i : ingredientsList.getAllEntries()) {
            str += "\n" + i.getName();
        }
        return str;
    }
}
