package duke.recipebook;

import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Dishes{

    private String dishname;
    private int total;
    private float rating;
    private List<String> ingredientsList = null;

    public Dishes(String name) {
        this.dishname = name;
    }

    public Dishes() {
        //
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

    public void addIngredients(String ingredients) {
        ingredientsList.add(ingredients);
    }

    public String toString() {
        return dishname;
    }
}
