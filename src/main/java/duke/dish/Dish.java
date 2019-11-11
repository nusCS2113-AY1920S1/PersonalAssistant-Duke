package duke.dish;

import duke.ingredient.Ingredient;
import duke.list.IngredientsList;
import duke.storage.Printable;

//@@author 9hafidz6
public class Dish implements Printable {

    private String dishname;
    private IngredientsList ingredientsList;

    /**
     * assigns dishname to name and ingredientList to il.
     * @param name name of the dish
     * @param il list of ingredient
     */
    public Dish(String name, IngredientsList il) {
        dishname = name;
        ingredientsList = il;
    }

    /**
     * assigns dishname to name and instantiate ingredientList.
     * @param name name of the dish
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
     * Get the name of the dish.
     * @return name of dish
     */
    public String getDishname() {
        return dishname;
    }

    public void changeName(String dishname) {
        this.dishname = dishname;
    }

    /**
     * Adding an ingredient to the ingredientList. If there already exist the same ingredient, update the amount.
     * If ingredient and amount is the same, then prints to user that it already exist in dish hence break.
     * @param ingredients new ingredient
     * @param amount new amount of ingredient
     * @return boolean flag to indicate if ingredient exist in the dish
     */
    public boolean addIngredients(Ingredient ingredients, int amount) {
        boolean flag = true;
        for(Ingredient i : ingredientsList.getAllEntries()) {
            //if they have the same ingredient name and amount
            if(i.getName().equals(ingredients.getName()) && i.getAmount() == amount) {
                System.out.println("\t ingredient already exist in dish");
                flag = false;
                break;
            }
            if(i.getName().equals(ingredients.getName())) {
                System.out.println("\t ingredient already exist in dish \n\t changed ingredient amount of: " + i.getName());
                System.out.println("\t from: " + i.getAmount());
                System.out.println("\t to: " + amount);
                i.setAmount(amount);
                flag = false;
                break;
            }
        }
        if(flag) {
            ingredientsList.addEntry(ingredients);
        }
        return flag;
    }

    //@@author CEGLincoln
    /**
     * A loop to get all the ingredients in the list.
     * @return all ingredients associated to the dish in string format
     */
    public String toString() {
        String str = "Recipe for " + dishname;
        for (Ingredient i : ingredientsList.getAllEntries()) {
            str += "\n\t " + i.getName() + ", " + i.getAmount();
        }
        return str; //Multi-line depending on the size of ingredient list
    }

    //@@author CEGLincoln
    @Override
    public String printInFile() {
        String str = dishname;
        for (Ingredient i : ingredientsList.getAllEntries()) {
            str += "|" + i.getName() + "|" + i.getAmount();
        }
        return str; //Single line
    }
}
