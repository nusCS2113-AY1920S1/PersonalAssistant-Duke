package duke.command;

import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import static duke.common.Messages.MESSAGE_REMIND;

/**
 * Handles the reminders command and inherits all the fields and methods of Command parent class.
 */
public class RemindCommand extends Command {

    /**
     * Constructor for class RemindCommand.
     * @param userInputCommand String containing input command from user
     */
    public RemindCommand(String userInputCommand) {
        this.userInputCommand = userInputCommand;
    }

    /**
     * Processes the reminders command to display all upcoming deadlines in task list.
     * @param taskList contains the task list
     * @param ui       deals with interactions with the user
     * @param storage  deals with loading tasks from the file and saving tasks in the file
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        System.out.println(MESSAGE_REMIND);
        for (int i = 0; i < taskList.remindDeadlineTask().size(); i++) {
            System.out.println("     " + (i + 1) + ". " + taskList.remindDeadlineTask().get(i));
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
