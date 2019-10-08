package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Handles the bye command and inherits all the fields and methods of Command parent class.
 */
public class ByeCommand extends CommandTest {

//    /**
//     * Display the exit message and the program.
//     * @param taskList contains the task list
//     * @param ui deals with interactions with the user
//     * @param storage deals with loading tasks from the file and saving tasks in the file
//     */
//    @Override
//    public void execute(TaskList taskList, Ui ui, Storage storage) {
//        ui.showGoodbye();
//        isExit();
//    }

    @Override
    public ArrayList<String> exe(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        return null;
    }

    @Override
    public void exec(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {

    }

    /**
     * A flag to raise a request to exit the program.
     * @return true if user wants to exit the program
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
