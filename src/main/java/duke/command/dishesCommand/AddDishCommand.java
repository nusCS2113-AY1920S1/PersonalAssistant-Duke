package duke.command.dishesCommand;

import duke.command.Command;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

public class AddDishCommand extends Command<Dish> {

    private Dish dish;

    public AddDishCommand(Dish dish) {
        //super(dish);
        this.dish = dish;
    }

    @Override
    public void execute(GenericList<Dish> dishList, Ui ui, Storage storage) throws DukeException {
        boolean flag = true;
        try {
            if(dishList.size() == 0) { //if the list is empty, immediately add dish in it
                dishList.addEntry(dish);
                ui.showAddedDishes(dish.getDishname());
                //storage.update();
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
                    //storage.update();
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