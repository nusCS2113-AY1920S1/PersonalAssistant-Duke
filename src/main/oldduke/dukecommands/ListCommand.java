package duke.command.dukecommands;

<<<<<<< HEAD:src/main/java/duke/command/ListCommand.java
import duke.exception.DukeException;
=======
import duke.command.Command;
>>>>>>> jiawei/chefduke:src/main/oldduke/dukecommands/ListCommand.java
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
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
     * @param userInput String containing input command from user
     */
    public ListCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param taskList contains the task list
     * @param ui deals with interactions with the user
     * @param storage deals with loading tasks from the file and saving tasks in the file
     */
<<<<<<< HEAD:src/main/java/duke/command/ListCommand.java
    @Override
    public ArrayList<String> feedback(TaskList taskList, Ui ui, Storage storage) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(MESSAGE_TASKED);
        arrayList.addAll(taskList.listTask());
        return arrayList;
=======

    public void execute(TaskList taskList, Ui ui, Storage storage) {
        System.out.println(MESSAGE_TASKED);
        for (int i = 0; i < taskList.listTask().size(); i++) {
            System.out.println(taskList.listTask().get(i));
        }
>>>>>>> jiawei/chefduke:src/main/oldduke/dukecommands/ListCommand.java
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
