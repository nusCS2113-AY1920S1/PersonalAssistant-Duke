package logic.parser.edit;

import common.DukeException;
import logic.command.Command;
import logic.command.edit.EditTaskDateTimeCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@@author JasonChanWQ

public class EditTaskDateTimeParser {

    public static final String EDIT_DATETIME_USAGE = "usage: edit task time [Index of task] /to [New DateTime]";
    public static final String INVALID_INDEX_OR_DATE_MESSAGE = "Not a valid task index or date!";
    public static final String EMPTY_INDEX_MESSAGE = "task index cannot be empty!";
    public static final String INVALID_DATETIME_MESSAGE = "Not a valid date time! Date time should be dd/MM/yyyy HHmm";
    public static final String TO_NOT_FOUND_MESSAGE = "Please input a /to";

    //@@author JasonChanWQ
    /**
     * Parses the user input and returns EditTaskDateTimeCommand
     * @param argument [index] /to [new date]
     * @return EditTaskDateTimeCommand
     * @throws DukeException exception
     */

    public static Command parseEditTaskDateTime(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(EDIT_DATETIME_USAGE);
        } else {
            String keyword = argument.trim();
            boolean isFound = keyword.indexOf("/to") != -1 ? true : false;

            if (isFound == true) {
                String[]arrOfStr = keyword.split(" /to ",2);

                try {
                    int taskIndex = Integer.parseInt(arrOfStr[0]);
                    String dateStr = arrOfStr[1];
                    int dd = Integer.parseInt(dateStr.substring(0,2));
                    int mM = Integer.parseInt(dateStr.substring(3,5));
                    int yyyy = Integer.parseInt(dateStr.substring(6,10));
                    int hH = Integer.parseInt(dateStr.substring(11,13));
                    int mm = Integer.parseInt(dateStr.substring(13,15));
                    if (dateStr.length() > 15 || dd > 31 || mM > 12 || yyyy > 9999 || hH > 24 || mm > 60) {
                        throw new DukeException(INVALID_DATETIME_MESSAGE);
                    }
                    Date newDate = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(dateStr);
                    return new EditTaskDateTimeCommand(taskIndex, newDate);

                } catch (NumberFormatException e) {
                    throw new DukeException(INVALID_INDEX_OR_DATE_MESSAGE);
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