package duke.command.dishesCommand;

import duke.command.ingredientCommand.AddCommand;
import duke.dish.DishList;
import duke.dish.Dish;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddDishCommand extends AddCommand<Dish> {

    private Dish dish;
    private int amount;
    private int Nb;


    public AddDishCommand(Dish dish, int amount) {
        super(dish);
        this.dish = dish;
        this.amount = amount;
    }

    public void execute(DishList dishList, Ui ui, Storage storage) throws DukeException {
        boolean flag = true;
        try {
            if(dishList.size() == 0) {
                dishList.addEntry(dish);
                dishList.getEntry(0).setNumberOfOrders(amount);
                ui.showAddedDishes(dish.getDishname(), amount);
            }
            else {
                for( int i = 0; i < dishList.size(); i++) {
                    if(dishList.getEntry(i).getDishname().equals(dish.getDishname())){
                        Nb = i;
                        flag = false; //dish already exist in list
                        break;
                    }
                }
                if(flag) {
                    dishList.addEntry(dish); // add dish into list found in dishes class
                    dishList.getEntry(dishList.size() - 1).setNumberOfOrders(amount);
                    ui.showAddedDishes(dish.getDishname(), dishList.getEntry(dishList.size() - 1).getTotalNumberOfOrders());
                }
                else {
                    dishList.getEntry(Nb).setNumberOfOrders(amount);
                    System.out.println("\t your updated orders:\n\t "
                            + dish.getDishname() + "\t amount: " +
                            String.valueOf(dishList.getEntry(Nb).getTotalNumberOfOrders()));
                }
            }
        } catch (Exception e) {
            throw new DukeException("unable to add dish");
        }
    }

}