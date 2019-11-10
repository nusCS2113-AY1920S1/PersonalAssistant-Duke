package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.edit.EditTaskNameCommand;

//@@author JasonChanWQ

public class EditTaskNameParser {

    public static final String EDIT_TASK_NAME_USAGE =
            "usage: edit task name [Index of Task] /to [New Name]";
    public static final String EMPTY_TASK_INDEX_MESSAGE = "[Index of Task] cannot be empty!";
    public static final String EMPTY_NEW_NAME_MESSAGE = "[NEW Name] cannot be empty!";
    public static final String INVALID_TASK_INDEX_MESSAGE = "Not a valid task index!";
    public static final String TO_NOT_FOUND_MESSAGE = "Please input a /to";

    //@@author JasonChanWQ
    /**
     * Parses the user input and returns EditTaskNameCommand
     * @param argument [Index of Task] /to [New Name]
     * @return EditTaskNameCommand
     * @throws DukeException exception
     */

    public static Command parseEditTaskName(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(EDIT_TASK_NAME_USAGE);
        } else {
            String keyword = argument.trim();
            boolean isFound = keyword.indexOf("/to") != -1 ? true : false;

            if (isFound == true) {
                try {
                    String[]arrOfStr = keyword.split("/to",2);
                    String indexOfTaskString = arrOfStr[0].trim();
                    String newName = arrOfStr[1].trim();
                    int indexOfTask = Integer.parseInt(indexOfTaskString);

                    if (newName.equals("")) {
                        throw new DukeException(EMPTY_NEW_NAME_MESSAGE);
                    } else {
                        return new EditTaskNameCommand(indexOfTask, newName);
                    }

                } catch (NumberFormatException e) {
                    throw new DukeException(INVALID_TASK_INDEX_MESSAGE);
                } catch (NullPointerException e) {
                    throw new DukeException(EMPTY_TASK_INDEX_MESSAGE);
                }
            } else {
                throw new DukeException(TO_NOT_FOUND_MESSAGE);
            }
        }
    }
}
