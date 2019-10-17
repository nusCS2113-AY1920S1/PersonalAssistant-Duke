package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;

public class DoneCommand extends Command<TaskList> {

    private int index;

    /**
     * Constructor for the duke.Commands.Command to mark a task in the duke.TaskList as 'done'
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public DoneCommand(String message) throws DukeException {
        this.message = message;
        try {
            index = Integer.parseInt(message.substring(5));
        } catch (Exception e) {
            throw new DukeException("","other");
        }
    }

    /**
     * Modifies the task list in use and returns the messages intended to be displayed.
     *
     * @param taskList the TaskList object that contains the task list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        if (taskList.getSize() == 0) {
            return "List is empty! Please enter a valid command.";
        }
        if (index > taskList.getSize() || index < 1) {
            return "Invalid index! Please try again.";
        } else {
            taskList.getTaskList().get(index - 1).setDone();
            try {
                //storage.updateFile(taskList);
            } catch (Exception e) {
                return "OOPS!!! An IO exception has occurred.";
            }
            return ui.formatDone(taskList.getTaskList(), index);
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

}
