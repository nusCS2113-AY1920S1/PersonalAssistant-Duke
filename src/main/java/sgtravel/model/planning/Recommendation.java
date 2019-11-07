package sgtravel.model.planning;

import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.RecommendationFailException;
import sgtravel.logic.parsers.ParserTimeUtil;

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
     * @return The itinerary based on the number of days of the trip.
     */

    public Itinerary makeItinerary(String[] itineraryDetails) throws ParseException, RecommendationFailException {
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());
        Itinerary itinerary = new Itinerary(start, end, "New Recommendation");

        int days = getNumberOfDays(start, end);
        if (days > 8) {
            throw new RecommendationFailException();
        }
        List<Agenda> agendaList1 = new ArrayList<>(agendaList.subList(0, days));

        assert (!agendaList1.isEmpty()) : "list should not be null";
        itinerary.setTasks(agendaList1);

        return itinerary;
    }

    private int getNumberOfDays(LocalDateTime start, LocalDateTime end) throws
            ChronologyBeforePresentException, ChronologyInconsistentException {
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new ChronologyBeforePresentException();
        } else if (end.isBefore(start) || start.isAfter(end)) {
            throw new ChronologyInconsistentException();
        } else {
            LocalDateTime tempDateTime = LocalDateTime.from(start);
            long days = tempDateTime.until(end, ChronoUnit.DAYS);
            return Integer.parseInt(String.valueOf(days)) + 1;
        }
    }

}
