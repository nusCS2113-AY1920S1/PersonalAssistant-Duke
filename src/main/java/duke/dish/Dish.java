package duke.dish;

import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.storage.Printable;

import java.util.ArrayList;
import java.util.List;

public class Dish implements Printable {

    private String dishname;
    private IngredientsList ingredientsList;

    public Dish(String name) {
        this.dishname = name;
        this.ingredientsList = new IngredientsList();
    }

    public int getIngredientSize() {
        return ingredientsList.size();
    }

    public Ingredient getIngredients(int index) {
        return ingredientsList.getEntry(index);
    }

    public String getDishname() {
        return dishname;
    }


    public void addIngredients(Ingredient ingredients) {
        ingredientsList.addEntry(ingredients);
    }

    public String toString() {
        String str = "";
        for (Ingredient i : ingredientsList.getAllEntries()) {
            str += i.getName() + ",";
        }
        return str;
    }

    @Override
    public String printInFile() {
        return null;
    }
}
