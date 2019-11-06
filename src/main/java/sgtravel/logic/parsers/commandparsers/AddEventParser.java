package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.AddCommand;
import sgtravel.logic.commands.Command;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.model.Event;

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
     * @throws ChronologyBeforePresentException If the dates are before now.
     * @throws ChronologyInconsistentException If the dates are inconsistent.
     */
    private Event createEvent(String userInput) throws ParseException,
            ChronologyBeforePresentException, ChronologyInconsistentException {
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
        checkLogicalDate(start, end);
        return new Event(withinDetails[ZERO].strip(), start, end);
    }

    /**
     * Checks if the dates provided is logical.
     * Does nothing if it's logical.
     * @param end The end date.
     * @param start The start date.
     * @throws ChronologyBeforePresentException If the dates are before now.
     * @throws ChronologyInconsistentException If the dates are inconsistent.
     */
    private void checkLogicalDate(LocalDateTime start, LocalDateTime end) throws ChronologyBeforePresentException,
            ChronologyInconsistentException {
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new ChronologyBeforePresentException();
        } else if (end.isBefore(start)) {
            throw new ChronologyInconsistentException();
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
