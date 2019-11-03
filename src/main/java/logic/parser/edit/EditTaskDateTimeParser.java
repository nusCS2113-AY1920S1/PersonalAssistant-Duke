package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.edit.EditTaskDateTimeCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@@author JasonChanWQ

public class EditTaskDateTimeParser {

    public static final String EDIT_DATETIME_USAGE = "usage: edit time [Index of task] /to [New DateTime]";
    public static final String INVALID_INDEX_MESSAGE = "Not a valid task index!";
    public static final String EMPTY_INDEX_MESSAGE = "task index cannot be empty!";
    public static final String INVALID_DATETIME_MESSAGE = "Not a valid date time! Date time should be dd/MM/yyyy HHmm";
    public static final String TO_NOT_FOUND_MESSAGE = "Please input a /to";

    /**
     * parse the snooze command, pass the index (in task list) and the new date to command
     * @param argument [index] /to [new date]
     * @return SnoozeCommand
     * @throws DukeException exception
     */
    //@@author JasonChanWQ
    public static Command parseEditTaskDateTime(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(EDIT_DATETIME_USAGE);
        } else {
            String keyword = argument.trim();
            boolean isFound = keyword.indexOf(" /to ") != -1 ? true : false;

            if (isFound == true) {
                String[]arrOfStr = keyword.split(" /to ",2);

                try {
                    int taskIndex = Integer.parseInt(arrOfStr[0]);
                    Date newDate = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(arrOfStr[1]);
                    return new EditTaskDateTimeCommand(taskIndex, newDate);

                } catch (NumberFormatException e) {
                    throw new DukeException(INVALID_INDEX_MESSAGE);
                } catch (NullPointerException e) {
                    throw new DukeException(EMPTY_INDEX_MESSAGE);
                } catch (ParseException e) {
                    throw new DukeException(INVALID_DATETIME_MESSAGE);
                }

            } else {
                throw new DukeException(TO_NOT_FOUND_MESSAGE);
            }
        }
    }
}