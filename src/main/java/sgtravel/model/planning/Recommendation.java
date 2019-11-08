package sgtravel.model.planning;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.RecommendationFailException;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.logic.parsers.commandparsers.CreateNewItineraryParser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recommended itinerary list provided by SGTravel.
 */
public class Recommendation {
    private List<Agenda> agendaList;

    /**
     * Constructor to initialise new Recommendation.
     *
     * @param agendaList The list containing all venues and todos for the itinerary.
     */
    public Recommendation(List<Agenda> agendaList) {
        this.agendaList = agendaList;
    }

    /**
     * Returns an itinerary by combining number of days entered by the user and this recommendation object.
     *
     * @param itineraryDetails contains all info to make an itinerary.
     * @return itinerary The recommended list based on the number of days of travel.
     */
    public Itinerary makeItinerary(String[] itineraryDetails) throws ParseException, RecommendationFailException {
        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[1].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[2].strip());


        Itinerary itinerary = new Itinerary(start, end, "New Recommendation");
        itinerary.checkValidDate();
        int days = itinerary.getNumberOfDays();

        if (days > 8) {
            throw new RecommendationFailException();
        }

        List<Agenda> agendaList1 = new ArrayList<>(agendaList.subList(0, days));

        assert (!agendaList1.isEmpty()) : "list should not be null";
        itinerary.setTasks(agendaList1);

        return itinerary;
    }
}
