package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.SnoozeCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//@@ JasonChanWQ

public class SnoozeCommandParser {

    public static final String SNOOZE_USAGE = "usage: snooze [Index of task] /to [New DateTime]";
    public static final String INVALID_INDEX_MESSAGE = "Not a valid task index!";
    public static final String EMPTY_INDEX_MESSAGE = "task index cannot be empty!";
    public static final String INVALID_DATETIME_MESSAGE = "Not a valid date time!";

    /**
     * parse the snooze command, pass the index (in task list) and the new date to command
     * @param argument [index] /to [new date]
     * @return SnoozeCommand
     * @throws DukeException exception
     */
    //@@ author JasonChanWQ
    public static Command parseSnoozeCommand(String argument) throws DukeException {
        if (argument.equals("")) {
            throw new DukeException(SNOOZE_USAGE);
        } else {
            String keyword = argument.trim();
            String[]arrOfStr = keyword.split(" /to ",2);

            try {
                int taskIndex = Integer.parseInt(arrOfStr[0]);
                Date newDate = new SimpleDateFormat("dd/MM/yyyy HHmm").parse(arrOfStr[1]);
                return new SnoozeCommand(taskIndex, newDate);

            } catch (NumberFormatException e) {
                throw new DukeException(INVALID_INDEX_MESSAGE);
            } catch (NullPointerException e) {
                throw new DukeException(EMPTY_INDEX_MESSAGE);
            } catch (ParseException e) {
                throw new DukeException(INVALID_DATETIME_MESSAGE);
            }

        }
    }
}