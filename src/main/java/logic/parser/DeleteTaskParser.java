package logic.parser;

import logic.command.DeleteTaskCommand;
import logic.command.Command;
import utils.DukeException;


public class DeleteTaskParser {


    public static final String TASK_INDEX_NO_EMPTY_MESSAGE = "The index of task cannot be empty.";

    public static Command parseDeleteTask(String argument) throws DukeException {

        if (argument != null) {
            int index = Integer.parseInt(argument);
            return new DeleteTaskCommand(index);
        } else {
            throw new DukeException(TASK_INDEX_NO_EMPTY_MESSAGE);
        }
    }
}
