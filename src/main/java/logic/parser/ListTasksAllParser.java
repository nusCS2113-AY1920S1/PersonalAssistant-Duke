package logic.parser;

import common.DukeException;
import logic.command.Command;
import logic.command.ListTasksCommand;
import logic.command.ListTasksPicNumCommand;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListTasksAllParser {

    /**
     * Parses add commands.
     */
    public static Command parseListTasksAll(String argument) throws DukeException {

        switch (argument) {
            case ListTasksParser.PICNUM:
                return new ListTasksPicNumCommand();

            case ListCommandParser.NONE:
                return new ListTasksCommand();

            default:
                throw new DukeException(ListTasksParser.LIST_USAGE);
        }

    }
}
