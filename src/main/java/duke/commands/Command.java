package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.Storage;
import duke.exceptions.BadInputException;
import duke.enums.CommandType;

/**
 * This is an abstract class.
 * Command objects are sent from the Parser and executed with TaskList or Ui.
 * Commands include: adding, deleting, marking as done etc.
 */

public class Command {


    protected CommandType type;

    //Currently the default constructor is a bad command
    public Command() {
        this.type = CommandType.BAD;
    }

    public Command(CommandType type) {
        this.type = type;
    }

    public CommandType getType() {
        return type;
    }

    /**
     * Executes the command.
     * Prints the list or saves the list and sends exit message.
     * Might need to separate into bye and list commands.
     */
    public void execute(TaskList list, Ui ui, Storage storage) throws BadInputException {
        if (type == CommandType.LIST) {
            int max = list.getSize();
            String listString = "";
            if (max == 0) {
                ui.print("The list is currently empty.");
                return;
            }

            for (int i = 0; i < max; i++) { //Index starts from 0.
                // TODO: Change to StringBuilder - Raghav
                listString += (i + 1 + ". " + list.getTask(i).toString() + "\n");
            }
            ui.print(listString);

        } else if (type == CommandType.REMINDER) {
            list.printReminders();

        } else if (type == CommandType.BYE) {
            storage.save(list.getTaskList());
            ui.printExitMessage();
        }
    }
}
