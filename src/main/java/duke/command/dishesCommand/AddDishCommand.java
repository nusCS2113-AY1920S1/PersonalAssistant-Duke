package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.Dish;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.ingredient.IngredientsList;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

public class AddDishCommand extends Command {

    private Dish dish;

    //constructor
    public AddDishCommand(Dish dish) {
        this.dish = dish;
    }

    //@@ Author 9hafidz6
    /**
     * adds a dish into the dishlist. if the dishlist is empty add immediately
     * else check through the whole list to check for duplicate, if no duplicate add dish into list
     * else do not add and notify user
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
        boolean flag = true;
        try {
            if(dishList.size() == 0) { //if the list is empty, immediately add dish in it
                dishList.addEntry(dish);
                ui.showAddedDishes(dish.getDishname());
                rs.addInFile(dish.printInFile());
            }
            else {
                for( int i = 0; i < dishList.size(); i++) { //check for duplicates in list
                    if(dishList.getEntry(i).getDishname().equals(dish.getDishname())){
                        flag = false; //dish already exist in list
                        break;
                    }
                }
                if(flag) { //if there are no duplicates
                    dishList.addEntry(dish); // add dish into list found in dishes class
                    ui.showAddedDishes(dish.getDishname());
                    rs.addInFile(dish.printInFile());
                }
                else { //if there are duplicates
                    System.out.println("\t dish already exist in list");
                }
            }
        } catch (Exception e) {
            throw new DukeException("unable to add dish");
        }
    }
}