package sgtravel.model.planning;

import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;
import sgtravel.model.locations.Venue;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Itinerary and its contained information.
 */
public class Itinerary {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private List<Agenda> list;

    /**
     * Constructor to initialise new Itinerary.
     * @param startDate The first day of the trip.
     * @param endDate The last day of the trip .
     * @param name The name of the itinerary.
     */
    public Itinerary(LocalDateTime startDate, LocalDateTime endDate, String name) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        list = new ArrayList<>();
    }

    /**
     * Prints the itinerary list in entirety to show on the UI.
     *
     * @return result The String which shows the itinerary in full
     */
    public String printItinerary() {
        int days = getNumberOfDays();
        StringBuilder result = new StringBuilder("Here are the list of locations for a "
                +  days + " day trip. This itinerary has the name : \n" + this.name + "\n");
        for (Agenda list1 : this.getList()) {
            result.append("\n");
            result.append("Day ").append(list1.getDay()).append(":").append("\n \n");
            result.append("Venues: ").append("\n");
            for (Venue venue : list1.getVenueList()) {
                result.append(venue.getAddress()).append("\n");
            }
            result.append("\n");
            result.append("Todo List: ").append("\n");
            for (Todo todo : list1.getTodoList()) {
                result.append(" - ").append(todo.getDescription()).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Checks if the dates of the itinerary are valid.
     *
     * @throws ChronologyBeforePresentException If start and end date are in the past.
     * @throws ChronologyInconsistentException If start ad end date are invalid.
     */
    public void checkValidDate() throws ChronologyBeforePresentException, ChronologyInconsistentException {
        if (startDate.isBefore(LocalDateTime.now()) || endDate.isBefore(LocalDateTime.now())) {
            throw new ChronologyBeforePresentException();
        } else if (endDate.isBefore(startDate) || startDate.isAfter(endDate)) {
            throw new ChronologyInconsistentException();
        }
    }

    /**
     * Returns number of days of the trip based on entered start and end dates.
     * @return the number of days of a trip (end - start).
     */
    public int getNumberOfDays() {
        LocalDateTime tempDateTime = LocalDateTime.from(startDate);
        long days = tempDateTime.until(endDate, ChronoUnit.DAYS);
        return Integer.parseInt(String.valueOf(days)) + 1;
    }

    /**
     * Returns the list of agendas associated with the itinerary.
     * @return list The agenda list.
     */
    public List<Agenda> getList() {
        return list;
    }

    /**
     * Replaces the contents of the current list with the updated one.
     * @param agenda The agenda list to replace the current one.
     */
    public void setTasks(List<Agenda> agenda) {
        list = agenda;
    }

    /**
     * Returns the start date of the trip.
     * @return endDate The first date of the trip
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Returns the end date of the trip.
     * @return endDate The last date of the trip
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns the name of the itinerary.
     * @return name Users name of the itinerary
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
