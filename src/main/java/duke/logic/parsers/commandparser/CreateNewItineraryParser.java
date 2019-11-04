package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.StartEndDateBeforeNowException;
import duke.commons.exceptions.StartEndDateDiscordException;
import duke.logic.commands.Command;
import duke.logic.commands.NewItineraryCommand;
import duke.logic.parsers.ParserTimeUtil;

import java.time.LocalDateTime;

public class CreateNewItineraryParser extends CommandParser {
    private LocalDateTime start;
    private LocalDateTime end;
    private String hotelLocation;
    private String name;
    private String[] itineraryDetails;

    /**
     * Parses user input into an Itinerary.
     *
     * @param input The User input
     * @throws ParseException If the input parsing fails.
     */
    public CreateNewItineraryParser(String input) throws ParseException,
            StartEndDateBeforeNowException, StartEndDateDiscordException {
        String[] itineraryDetails = input.substring("newItinerary".length()).strip().split(" ");

        start = ParserTimeUtil.parseStringToDate(itineraryDetails[0].strip());
        end = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new StartEndDateBeforeNowException();
        } else if (end.isBefore(start) || start.isAfter(end)) {
            throw new StartEndDateDiscordException();
        }
        hotelLocation = itineraryDetails[2].strip();
        name = itineraryDetails[3].strip();
        this.itineraryDetails = itineraryDetails;
    }

    @Override
    public Command parse() {
        return new NewItineraryCommand(start, end, hotelLocation, name, itineraryDetails);
    }
}
