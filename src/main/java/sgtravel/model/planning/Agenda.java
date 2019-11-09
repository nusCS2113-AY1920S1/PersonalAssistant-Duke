package sgtravel.model.planning;

import sgtravel.model.lists.VenueList;
import sgtravel.model.locations.Venue;

import java.util.List;

/**
 * Represents an Agenda - places to go and things to do within a day.
 */
public class Agenda extends VenueList {
    private List<Todo> todoList;
    private int dayNumber;

    /**
     * Constructor to initialise new Agenda object.
     *
     * @param todoList The list of things to do in a day.
     * @param venueList The list of places to go in a ay .
     * @param dayNumber The agenda's serial number.
     */
    public Agenda(List<Todo> todoList, List<Venue> venueList, int dayNumber) {
        super(venueList);
        this.dayNumber = dayNumber;
        this.todoList = todoList;
    }

    /**
     * This makes the list of agendas for a newly entered Itinerary.
     *
     * @return result The Agenda represented as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Agenda |").append(this.getDay()).append("\n");
        for (Venue venue : this.getVenueList()) {
            result.append(venue.toString());
            result.append("\n");
        }
        for (Todo todo : this.getTodoList()) {
            result.append(todo.toString());
            result.append("|");
        }
        result.append("\n");
        return result.toString();
    }

    /**
     * Returns the day number of the specific agenda.
     *
     * @return dayNumber The serial number of the agenda.
     */
    public int getDay() {
        return dayNumber;
    }

    /**
     * Returns the todo list of an agenda.
     *
     * @return todoList The list containing all of the things to do.
     */
    public List<Todo> getTodoList() {
        return this.todoList;
    }

    /**
     * Updates the value of day.
     *
     * @param dayNumber The new day number to be set.
     */
    public void setDay(int dayNumber) {
        this.dayNumber = dayNumber;
    }

}
