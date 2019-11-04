package duke.logic.parsers.commandParsers;

import duke.commons.Messages;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.ParseException;
import duke.logic.commands.AddCommand;
import duke.logic.commands.Command;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;

import java.time.LocalDateTime;

/**
 * Parses the user inputs into suitable format for AddCommand.
 */
public class AddEventParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    /**
     * Constructs the AddEventParser.
     */
    public AddEventParser(String input) {
        this.input = input;
    }

    /**
     * Returns a new Event constructed from user input.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Event object.
     * @throws ParseException If Event object cannot be created from user input.
     */
    private static Event createEvent(String userInput) throws ParseException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between| and");
        if (withinDetails.length == ONE) {
            throw new ParseException(Messages.ERROR_DESCRIPTION_EMPTY);
        }
        if (withinDetails.length != THREE || withinDetails[ONE] == null || withinDetails[TWO] == null) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }
        if (withinDetails[ZERO].strip().isEmpty()) {
            throw new ParseException(Messages.ERROR_DESCRIPTION_EMPTY);
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[ONE].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[TWO].strip());
        try {
            return new Event(withinDetails[ZERO].strip(), start, end);
        } catch (ApiException e) {
            throw new ParseException(Messages.ERROR_API_DATA_NULL);
        }
    }

    /**
     * Parses user input and constructs an AddCommand object.
     * @return AddCommand object.
     * @throws ParseException If Event object cannot be created from user input.
     */
    @Override
    public Command parse() throws ParseException {
        Event event = createEvent(input);
        return new AddCommand(event);
    }
}
