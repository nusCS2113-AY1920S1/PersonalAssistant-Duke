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
import java.util.Map;

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


    @Override
    public boolean exe(){
//        mainWindow.handleListTask();
//        ui.showListTask();
        return false;
//        System.out.println(MESSAGE_TASKED);
//        for (int i = 0; i < taskList.listTask().size(); i++) {
//            System.out.println(taskList.listTask().get(i));
//        }
    }

    @Override
    public void exec(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
        ui.showListTask();
    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public ArrayList<String> feedback(TaskList taskList, Ui ui, Storage storage) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(MESSAGE_TASKED);
        arrayList.addAll(taskList.listTask());
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
