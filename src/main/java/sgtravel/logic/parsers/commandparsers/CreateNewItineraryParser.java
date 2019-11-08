package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.NewItineraryCommand;
import sgtravel.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;

/**
 * Parses the user inputs into suitable format for CreateNewItineraryCommand.
 */
public class CreateNewItineraryParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    /**
     * Constructs the CreateNewItineraryParser.
     *
     * @param input The user input.
     */
    public CreateNewItineraryParser(String input) {
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
        String[] itineraryDetails = input.substring("newItinerary".length()).strip().split(" ");
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[ZERO].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[ONE].strip());

        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new ChronologyBeforePresentException();
        } else if (end.isBefore(start) || start.isAfter(end)) {
            throw new ChronologyInconsistentException();
        }
        String name = itineraryDetails[TWO].strip();
        return new NewItineraryCommand(start, end, name, itineraryDetails);

    }
}
