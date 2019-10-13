package duke.commands;

import duke.tasks.MealList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.user.User;

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
     * This function will execute the exit command.
     * @param tasks the TaskList object
     * @param ui the ui object to display the user interface of an "exit" command
     * @param storage the storage object that stores the list of meals
     * @param in
     */
    @Override
    public void execute(MealList tasks, Ui ui, Storage storage, User user, Scanner in) {
        ui.showBye();
    }
}
