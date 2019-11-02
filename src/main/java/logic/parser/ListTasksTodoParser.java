package logic.parser;

import common.DukeException;
import logic.command.*;

public class ListTasksTodoParser {

    /**
     * Parses add commands.
     */
    public static Command parseListTasksTodo(String argument) throws DukeException {

        switch (argument) {
            case ListTasksParser.PICNUM:
                return new ListTasksTodoPicNumCommand();

            case ListCommandParser.NONE:
                return new ListTasksTodoCommand();

            default:
                throw new DukeException(ListTasksParser.LIST_USAGE);
        }

    }
}
