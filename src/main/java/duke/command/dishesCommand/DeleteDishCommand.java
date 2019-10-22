package duke.command.dishesCommand;

import duke.Dishes.DishList;
import duke.command.Cmd;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.ui.Ui;

public class DeleteDishCommand extends Cmd<DishList> {

    private int Nb;

    public DeleteDishCommand(int dishNb) {
        this.Nb = dishNb;
    }

    @Override
    public void execute(DishList dish1, Ui ui, Storage storage) throws DukeException {
        try {
            ui.showDeletedDIsh(dish1.getDish(Nb - 1).getDishname());
            dish1.deleteDish(Nb - 1);
        } catch (Exception e) {
            throw new DukeException("dish does not exist");
        }
    }
}