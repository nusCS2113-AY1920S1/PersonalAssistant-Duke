package duke.dish;

import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.storage.Printable;

public class Dish implements Printable {

    private String dishname;
    private IngredientsList ingredientsList;

    public Dish(String name, IngredientsList il) {
        dishname = name;
        ingredientsList = il;
    }

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
        String str = "Recipe for " + dishname;
        for (Ingredient i : ingredientsList.getAllEntries()) {
            str += "\n" + i.getName() + ", " + i.getAmount();
        }
        return str; //Multi-line depending on the size of ingredient list
    }

    @Override
    public String printInFile() {
        String str = dishname;
        for (Ingredient i : ingredientsList.getAllEntries()) {
            str += "|" + i.getName() + "|" + i.getAmount();
        }
        return str; //Single line
    }
}
