package logic.parser;

import logic.command.DeleteTaskCommand;
import logic.command.Command;
import common.DukeException;


public class DeleteTaskParser {


    public static final String TASK_INDEX_NO_EMPTY_MESSAGE = "The index of task cannot be empty.";

    //@@author yuyanglin28

    /**
     * parse the delete task command, pass the index (in task list) to command
     * @param argument index of the task (string) to be deleted
     * @return delete task command
     * @throws DukeException exception
     */
    public static Command parseDeleteTask(String argument) throws DukeException {

        if (argument != null) {
            int index = Integer.parseInt(argument.trim());
            return new DeleteTaskCommand(index);
        } else {
            throw new DukeException(TASK_INDEX_NO_EMPTY_MESSAGE);
        }
    }
}
