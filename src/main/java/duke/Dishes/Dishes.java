package duke.Dishes;

import java.util.ArrayList;
import java.util.List;

public class Dishes {

    private String dishname;
    private int total = 0;
    private float rating = 0;
    private List<String> ingredientsList;

    public Dishes(String name) {
        this.dishname = name;
        this.ingredientsList = new ArrayList<>();
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

//    public String toString() {
//        return dishname;
//    }
}