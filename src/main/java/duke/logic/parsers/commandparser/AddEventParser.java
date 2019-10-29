package duke.logic.parsers.commandparser;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.DukeUnknownCommandException;
import duke.logic.commands.AddCommand;
import duke.logic.commands.Command;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;

import java.time.LocalDateTime;

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
        event = createEvent(input);
    }

    /**
     * Parses the userInput and return a new Event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Event object.
     */
    public static Event createEvent(String userInput) throws DukeException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between| and");
        if (withinDetails.length == 1) {
            throw new DukeUnknownCommandException();
        }
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new DukeException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new DukeException(Messages.ERROR_DESCRIPTION_EMPTY);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        return new Event(withinDetails[0].strip(), start, end);
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
