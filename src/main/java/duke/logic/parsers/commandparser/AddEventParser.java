package duke.logic.parsers.commandparser;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.AddCommand;
import duke.logic.commands.Command;
import duke.logic.parsers.ParserUtil;
import duke.model.Event;

/**
 * Parses the user inputs into suitable format for AddCommand.
 */
public class AddEventParser extends CommandParser {
    private Event event;

    /**
     * Parses user input into event.
     * @param input The User input
     */
    public AddEventParser(String input) throws DukeException {
        event = ParserUtil.createEvent(input);
    }

    /**
     * Constructs AddCommand object.
     * @return AddCommand object
     */
    @Override
    public Command parse() throws DukeException {
        return new AddCommand(event);
    }
}
