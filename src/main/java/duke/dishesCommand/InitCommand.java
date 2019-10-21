package duke.dishesCommand;

import duke.exception.DukeException;
import duke.Dishes.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.Dishes.Dishes;

public class InitCommand extends RecipeCommand {

    public InitCommand() {
        //clears all the amount in dishes
    }

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        System.out.println("\t are you sure you want to clear list? (yes or no)");
        String command = ui.readCommand();
        if(command.equals("yes")){
            dish1.clearList();
            System.out.println("\t LIST IS CLEARED");
        }
        else if(command.equals("no")){
            System.out.println("\t LIST IS NOT CLEARED");
        }
    }
}
