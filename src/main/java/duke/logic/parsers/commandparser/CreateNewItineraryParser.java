package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.StartEndDateBeforeNowException;
import duke.commons.exceptions.StartEndDateDiscordException;
import duke.logic.commands.Command;
import duke.logic.commands.NewItineraryCommand;
import duke.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;

/**
 * Parses the user inputs into suitable format for CreateNewItineraryCommand.
 */
public class CreateNewItineraryParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    /**
     * Constructs the CreateNewItineraryParser.
     *
     * @param input The User input
     */
    public CreateNewItineraryParser(String input) {
        this.input = input;
    }

    /**
     * Parses user input and constructs an NewItineraryCommand object.
     * @return NewItineraryCommand object.
     * @throws ParseException If NewItineraryCommand object cannot be created from user input.
     */
    @Override
    public Command parse() throws ParseException {
        String[] itineraryDetails = input.substring("newItinerary".length()).strip().split(" ");
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[ZERO].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[ONE].strip());
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new StartEndDateBeforeNowException();
        } else if (end.isBefore(start) || start.isAfter(end)) {
            throw new StartEndDateDiscordException();
        }
        String hotelLocation = itineraryDetails[TWO].strip();
        String name = itineraryDetails[THREE].strip();
        return new NewItineraryCommand(start, end, hotelLocation, name, itineraryDetails);
    }
}
