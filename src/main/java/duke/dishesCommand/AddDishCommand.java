package duke.dishesCommand;

import duke.exception.DukeException;
import duke.Dishes.Dishes;
import duke.Dishes.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddDishCommand extends RecipeCommand {

    private Dishes dish;
    private int amount;
    private int Nb;

    public AddDishCommand(Dishes dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        boolean flag = true;
        try {
            if(dish1.getSize() == 0) {
                dish1.addDish(dish);
                dish1.getDish(0).setNumberOfOrders(amount);
                ui.showAddedDishes(dish.getDishname(), amount);
            }
            else {
                for( int i = 0; i < dish1.getSize(); i++) {
                    if(dish1.getDish(i).getDishname().equals(dish.getDishname())){
                        Nb = i;
                        flag = false; //dish already exist in list
                        break;
                    }
                }
                if(flag) {
                    dish1.addDish(dish); // add dish into list found in dishes class
                    dish1.getDish(dish1.getSize() - 1).setNumberOfOrders(amount);
                    ui.showAddedDishes(dish.getDishname(), dish1.getDish(dish1.getSize() - 1).getTotalNumberOfOrders());
                }
                else {
                    dish1.getDish(Nb).setNumberOfOrders(amount);
                    System.out.println("\t your updated orders:\n\t "
                            + dish.getDishname() + "\t amount: " +
                            String.valueOf(dish1.getDish(Nb).getTotalNumberOfOrders()));
                }
            }
        } catch (Exception e) {
            throw new DukeException("unable to add dish");
        }
    }
}
