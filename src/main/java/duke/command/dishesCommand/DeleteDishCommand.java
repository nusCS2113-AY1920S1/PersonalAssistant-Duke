package duke.command.dishesCommand;

import duke.command.ingredientCommand.DeleteCommand;
import duke.dish.Dish;
import duke.dish.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;

public class DeleteDishCommand extends DeleteCommand<Dish> {

    private int Nb;

    public DeleteDishCommand(int dishNb) {
        super(dishNb);
        this.Nb = dishNb;
    }

    public void execute(DishList dish1, Ui ui, Storage storage) throws DukeException {
        try {
            ui.showDeletedDIsh(dish1.getEntry(Nb - 1).getDishname());
            dish1.removeEntry(Nb - 1);
        } catch (Exception e) {
            throw new DukeException("dish does not exist");
        }
    }
}