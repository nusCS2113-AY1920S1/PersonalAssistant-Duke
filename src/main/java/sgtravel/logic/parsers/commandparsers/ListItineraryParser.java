package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.ListItineraryCommand;

/**
 * Parses the user inputs into suitable format for ListItineraryCommand.
 */
public class ListItineraryParser extends CommandParser {
    private String input;

    /**
     * Constructs the ListItineraryParser.
     *
     * @param input The user input.
     */
    public ListItineraryParser(String input) {
        this.input = input;
    }

    /**
     * Parses user input and constructs an NewItineraryCommand object.
     *
     * @return NewItineraryCommand object.
     * @throws ParseException If NewItineraryCommand object cannot be created from user input.
     */
    @Override
    public Command parse() throws ParseException {
        String[] list = input.substring("listItinerary".length()).strip().split(" ");
        if(list.length != 0) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        } else {
            return new ListItineraryCommand();
        }
    }
}
