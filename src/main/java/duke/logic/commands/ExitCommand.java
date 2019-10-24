package duke.logic.commands;

import duke.model.MealList;
import duke.model.TransactionList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.model.user.User;

import java.util.Scanner;

/**
 * ExitCommand is a public class that extends from the abstract class Command.
 */
public class ExitCommand extends Command {
    /**
     * isExit() is a function that will return true if called, to indicate the the program is going to exit.
     * @return <code>true</code> if the function is called
     */
    public boolean isExit() {
        return true;
    }

    /**
     * Executes the exit command.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param ui the ui object to display the results of the command to the user
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param in the scanner object to handle secondary command IO
     */
    @Override
    public void execute(MealList meals, Ui ui, Storage storage, User user, Scanner in, TransactionList transactions) {
        ui.showBye();
    }
}
