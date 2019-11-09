package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.edit.EditTaskTimeCommand;
import logic.parser.ArgumentTokenizer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EditTaskTimeParser {

    public static final String EDIT_USAGE = "Usage: edit [task] [time/des] [index] /to ...";
    private static final String NAME_NO_EMPTY = "the name of task shouldn't be empty.";
    private static final String CHANGE_NO_EMPTY = "put description after /to";
    public static final String INVALID_DATETIME_MESSAGE = "Not a valid date time, "
                                                          + "the time format should be dd/MM/yyyy HHmm";
    private static final String GET_INDEX_FAIL = "After edit type, put a valid index.";

    //@@author yuyanglin28
    /**
     * parse delete command, divide to task or member
     * @param partialCommand argument part of the command
     * @return a delete command
     * @throws DukeException exception
     */
    public static Command parseEditTaskTime(String partialCommand) throws DukeException {

        HashMap<String, String> argumentMultimap = ArgumentTokenizer.tokenize(partialCommand);
        String name = argumentMultimap.get("");
        String changeContent = argumentMultimap.get("/to");

        if (name.length() == 0) {
            throw new DukeException(NAME_NO_EMPTY + "\n" + EDIT_USAGE);
        } else if (changeContent.length() == 0) {
            throw new DukeException(CHANGE_NO_EMPTY + "\n" + EDIT_USAGE);
        } else {
            name = name.trim();
            changeContent = changeContent.trim();
            try {
                Date newDate = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(changeContent);
                int indexInList = Integer.parseInt(name);
                return new EditTaskTimeCommand(indexInList, newDate);
            } catch (Exception e) {
                throw new DukeException(INVALID_DATETIME_MESSAGE + " or " + GET_INDEX_FAIL);
            }

        }
    }
}
