package duke.model.planning;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.StartEndDateBeforeNowException;
import duke.commons.exceptions.StartEndDateDiscordException;
import duke.logic.api.ApiParser;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.locations.Venue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Itinerary and its contained information.
 */
public class Recommendation {
    private List<Agenda> agendaList;

    /**
     * Constructor to initialise new Itinerary.
     */
    public Recommendation(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    /**
     * Returns number of days of the trip based on entered start and end dates.
     *
     * @return The number of days of the trip
     * @param itineraryDetails
     */

    public Itinerary makeItinerary(String[] itineraryDetails) throws ParseException, ApiException,
            StartEndDateBeforeNowException, StartEndDateDiscordException {
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[0].strip());

        Itinerary itinerary = new Itinerary(start, end, hotelLocation, "New Recommendation");

        List<Agenda> agendaList1 = new ArrayList<>(agendaList.subList(0,getNumberOfDays(start, end)));

        assert (!agendaList1.isEmpty()) : "list should not be null";
        itinerary.setTasks(agendaList1);

        return itinerary;
    }

    private int getNumberOfDays(LocalDateTime start, LocalDateTime end) throws
            StartEndDateBeforeNowException, StartEndDateDiscordException {
        if(start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new StartEndDateBeforeNowException();
        } else if (end.isBefore(start) || start.isAfter(end)) {
            throw new StartEndDateDiscordException();
        } else {
            LocalDateTime tempDateTime = LocalDateTime.from(start);
            long days = tempDateTime.until(end, ChronoUnit.DAYS);
            return Integer.parseInt(String.valueOf(days)) + 1;
        }
    }

}
