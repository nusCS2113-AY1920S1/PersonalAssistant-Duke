package duke.command.ingredientCommand;

import duke.command.Command;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents a specific {@link Command} used to exit the program after the user inputs "bye".
 */
public class ExitCommand<T> extends Command<T> {

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(GenericList IngredientsList, Ui ui, Storage storage) {
        System.out.println("\t Bye. Hope to see you again soon!");
    }
}
