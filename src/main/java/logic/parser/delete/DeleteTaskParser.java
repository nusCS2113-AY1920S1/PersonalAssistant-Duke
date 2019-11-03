package logic.parser.delete;

import logic.command.delete.DeleteTaskCommand;
import logic.command.Command;
import common.DukeException;


public class DeleteTaskParser {


    public static final String TASK_INDEX_NO_EMPTY_MESSAGE = "The index of task cannot be empty.";

    //@@author yuyanglin28
    /**
     * parse the delete task command, pass the index (in task list) to command
     * @return delete task command
     * @throws DukeException exception
     */
    public static Command parseDeleteTask(int[] indexes) throws DukeException {

        if (indexes != null) {
            return new DeleteTaskCommand(indexes);
        } else {
            throw new DukeException(TASK_INDEX_NO_EMPTY_MESSAGE);
        }
    }
}
