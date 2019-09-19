package duke.command;

import duke.task.TaskList;
import duke.task.DukeException;
import duke.task.Ui;
import duke.task.Storage;
import duke.task.ToDo;


/**
 * Represents the command to add a To Do.
 */
public class AddToDoCommand extends Command {
    /**
     * Takes in a flag to represent if it should exit and the input given by the User.
     * @param isExit True if the program should exit after running this command, false otherwise
     * @param input Input given by the user
     */
    public AddToDoCommand(Boolean isExit, String input) {
        super(isExit, input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (input.length() < 6) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }

        input = input.substring(5);

        ToDo toAdd = new ToDo(input);
        taskList.addToArrayList(toAdd);
        ui.output = "Got it. I've added this task: \n  " + toAdd.toString()
                + "\nNow you have " + taskList.getSize() + " task(s) in the list.";
        storage.saveToFile();
    }

}
