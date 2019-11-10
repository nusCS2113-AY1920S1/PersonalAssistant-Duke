package logic.parser.list;

import common.DukeException;
import logic.command.Command;
import logic.command.list.ListRemindersCommand;

public class ListRemindersParser {

    //@@author AugGust

    /**
     * Parses for "list reminders"
     * @param argument not used for this parser
     * @return Command to list reminders
     * @throws DukeException dnot used for this parser
     */
    public static Command parseReminders(String argument) throws DukeException {
        return new ListRemindersCommand();
    }
}
