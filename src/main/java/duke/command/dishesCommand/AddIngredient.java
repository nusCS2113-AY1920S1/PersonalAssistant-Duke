package duke.command.dishesCommand;

import duke.command.Command;
import duke.list.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.Ingredient;
import duke.list.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

//Adds an ingredient to the dish
public class AddIngredient extends Command {

    private Ingredient ingredient;
    private int index;
    private int amount;

    //constructor
    public AddIngredient(Ingredient ingredient, int amount, int index) {
        super();
        this.ingredient = ingredient;
        this.index = index;
        this.amount = amount;
    }

    //@@ Author 9hafidz6

    /**
     * adds an ingredient to the dish. if the ingredient already exist and amount is the same
     * prints out ingredient already exist
     * @param fridge ingredients found in fridge
     * @param dishList list of dishes
     * @param ol list of orders
     * @param ui prints output for user
     * @param fs storage for fridge
     * @param os storage for order
     * @param rs storage for recipe
     * @throws DukeException
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os, RecipeStorage rs) throws DukeException {
        try {
            boolean flag;
            flag = dishList.getEntry(index - 1).addIngredients(ingredient,amount);
            rs.changeContent(index - 1);
            //rs.removeFromFile(index - 1);
            //rs.addInFile(dishList.getEntry(index - 1).printInFile());
            if(flag) {
                ui.showIngredients(ingredient,dishList.getEntry(index - 1));
            }
        } catch (Exception e) {
            throw new DukeException("cannot add ingredient, enter a valid command");
        }
    }
}
