package duke.logic.parsers.commandparser;

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
    private Event event;

    /**
     * Parses user input into event.
     * @param input The User input
     */
    public AddEventParser(String input) throws ParseException {
        event = createEvent(input);
    }

    /**
     * Parses the userInput and return a new Event constructed from it.
     *
     * @param userInput The userInput read by the user interface.
     * @return The new Event object.
     */
    public static Event createEvent(String userInput) throws ParseException {
        String[] withinDetails = userInput.substring("event".length()).strip().split("between| and");
        if (withinDetails.length == 1) {
            throw new ParseException();
        }
        if (withinDetails.length != 3 || withinDetails[1] == null || withinDetails[2] == null) {
            throw new ParseException();
        }
        if (withinDetails[0].strip().isEmpty()) {
            throw new ParseException();
        }
        LocalDateTime start = ParserTimeUtil.parseStringToDate(withinDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(withinDetails[2].strip());
        try {
            return new Event(withinDetails[0].strip(), start, end);
        } catch (ApiException e) {
            throw new ParseException();
        }
    }

    /**
     * Constructs AddCommand object.
     * @return AddCommand object
     */
    @Override
    public Command parse() {
        return new AddCommand(event);
    }
}
