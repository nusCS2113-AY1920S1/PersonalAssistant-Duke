package duke.command;

import duke.Main;
import duke.MainWindow;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.MESSAGE_TASKED;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListCommand extends CommandTest {

    /**
     * Constructor for class ListCommand.
     * @param userInputCommand String containing input command from user
     */
    public ListCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

//    @Override
//    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
//
//    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public ArrayList<String> exe(TaskList taskList, Ui ui, Storage storage) {
//        mainWindow.handleListTask();
//        ui.showListTask();
        return taskList.listTask();
//        System.out.println(MESSAGE_TASKED);
//        for (int i = 0; i < taskList.listTask().size(); i++) {
//            System.out.println(taskList.listTask().get(i));
//        }
    }

    @Override
    public void exec(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        ui.showListTask();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
