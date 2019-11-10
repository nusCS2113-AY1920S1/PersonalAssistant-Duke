package logic.parser.delete;

import logic.command.delete.DeleteTaskCommand;
import logic.command.Command;
import common.DukeException;


public class DeleteTaskParser {

    private static final String EMPTY_INDEX = "Task index cannot be empty.";
    private static final String WRONG_INDEX_FORMAT = "Wrong number format, please use space to separate indexes.";

    //@@author yuyanglin28
    /**
     * parse the delete task command, pass the index (in task list) to command
     * @return delete task command
     * @throws DukeException throw exception when index is empty
     */
    public static Command parseDeleteTask(String arguments) throws DukeException {
;
        int[] indexes = null;

        if (arguments != null) {
            arguments = arguments.trim();
            String[] indexesString = arguments.split("\\s+");
            indexes = new int[indexesString.length];
            for (int i = 0; i < indexes.length; i++) {
                try {
                    int index = Integer.parseInt(indexesString[i]);
                    indexes[i] = index;
                } catch (NumberFormatException e) {
                    throw new DukeException(WRONG_INDEX_FORMAT);
                }
            }
        }

        if (indexes != null) {
            return new DeleteTaskCommand(indexes);
        } else {
            throw new DukeException(EMPTY_INDEX);
        }
    }
}
