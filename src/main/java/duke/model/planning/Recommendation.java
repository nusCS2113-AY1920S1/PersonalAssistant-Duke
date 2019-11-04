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
     * @param itineraryDetails contains all info to make an itinerary.
     * @return The number of days of the trip
     */

    public Itinerary makeItinerary(String[] itineraryDetails) throws ParseException,
            StartEndDateBeforeNowException, StartEndDateDiscordException {
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        try {
            Venue hotelLocation = ApiParser.getLocationSearch(itineraryDetails[0].strip());
            return getAgenda(start, end, hotelLocation);
        } catch (ApiException e) {
            Venue hotelLocation = new Venue("DUMMY LOCATION", 1.3973210291170202,
                    103.753758637401, 0, 0);
            return getAgenda(start, end, hotelLocation);
        }
    }

    private Itinerary getAgenda(LocalDateTime start, LocalDateTime end, Venue hotelLocation) throws
            StartEndDateBeforeNowException, StartEndDateDiscordException {
        Itinerary itinerary = new Itinerary(start, end, hotelLocation, "New Recommendation");

        List<Agenda> agendaList1 = new ArrayList<>(agendaList.subList(0, getNumberOfDays(start, end)));

        assert (!agendaList1.isEmpty()) : "list should not be null";
        itinerary.setTasks(agendaList1);

        return itinerary;
    }

    private int getNumberOfDays(LocalDateTime start, LocalDateTime end) throws
            StartEndDateBeforeNowException, StartEndDateDiscordException {
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
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
