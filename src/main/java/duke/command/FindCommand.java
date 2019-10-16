package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.list.tasklist.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.COMMAND_FIND;

/**
 * Handles the find command and inherits all the fields and methods of Command parent class.
 */
public class FindCommand extends CommandTest {

    /**
     * Constructor for class FindCommand.
     * @param userInput String containing input command from user
     */
    public FindCommand(String userInput) {
        this.userInput = userInput;
    }
//    /**
//     * Processes the find command to search for tasks in task list.
//     * @param taskList contains the task list
//     * @param ui deals with interactions with the user
//     * @param storage deals with loading tasks from the file and saving tasks in the file
//     * @throws DukeException if Duke cannot recognize the user input or there is no matching task found in tbe list
//     */
//    @Override
//    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
//        if (userInput.trim().equals(COMMAND_FIND)) {
//            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//        } else if (userInput.trim().charAt(4) == ' ') {
//            String description = userInput.split("\\s", 2)[1].trim();
//            System.out.println(MESSAGE_FIND);
//            for (int i = 0; i < taskList.findTask(description).size(); i++) {
//                System.out.println("     " + (i + 1) + ". " + taskList.findTask(description).get(i));
//            }
//        } else {
//            throw new DukeException(ERROR_MESSAGE_RANDOM);
//        }
//    }

    @Override
    public ArrayList<String> execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_FIND)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//            ui.showMessage(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(4) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            arrayList.add(null);
            arrayList.addAll(taskList.findTask(description));
//            ui.showMessage(MESSAGE_FIND);
//            return taskList.findTask(description);
//            for (int i = 0; i < taskList.findTask(description).size(); i++) {
//                System.out.println("     " + (i + 1) + ". " + taskList.findTask(description).get(i));
//            }
        }
        return arrayList;
    }


//    @Override
//    public void exec(TaskList taskList, Ui ui, Storage storage) throws DukeException, ParseException {
//        if (userInput.trim().equals(COMMAND_FIND)) {
//            ui.showMessage(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//        } else if (userInput.trim().charAt(4) == ' ') {
//            String description = userInput.split("\\s", 2)[1].trim();
//            ui.showMessage(MESSAGE_FIND);
//            return taskList.findTask(description);
//            for (int i = 0; i < taskList.findTask(description).size(); i++) {
//                System.out.println("     " + (i + 1) + ". " + taskList.findTask(description).get(i));
//            }
//        } else {
//            ui.showMessage(ERROR_MESSAGE_RANDOM);
//            throw new DukeException(ERROR_MESSAGE_RANDOM);
//        }
//    }

    @Override
    public boolean isExit() {
        return false;
    }
}
