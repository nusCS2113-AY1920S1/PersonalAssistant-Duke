package duke.dish;

import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.storage.Printable;

//@@author 9hafidz6

public class Dish implements Printable {

    private String dishname;
    private IngredientsList ingredientsList;

    /**
     * assigns dishname to name and ingredientList to il
     * @param name name of the dish
     * @param il list of ingredient
     */
    public Dish(String name, IngredientsList il) {
        dishname = name;
        ingredientsList = il;
    }

    /**
     * assigns dishname to name and instantiate ingredientList
     * @param name
     */
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

    /**
     * get the name of the dish
     * @return name of dish
     */
    public String getDishname() {
        return dishname;
    }

    public void changeName(String dishname) {
        this.dishname = dishname;
    }

    /**
     * adding an ingredient to the ingredientList
     * @param ingredients to be added to list of ingredients
     */
    public void addIngredients(Ingredient ingredients) {
        ingredientsList.addEntry(ingredients);
    }

    /**
     * a loop to get all the ingredients in the list
     * @return all ingredients associated to the dish in string format
     */
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
