package sgtravel.model.planning;

import sgtravel.model.lists.VenueList;
import sgtravel.model.locations.Venue;

import java.util.List;

/**
 * Represents an Agenda - places to go and things to do within a day.
 */
public class Agenda extends VenueList {
    private List<Todo> todoList;
    private int day;

    /**
     * Constructor to initialise new Day object.
     */
    public Agenda(List<Todo> todoList, List<Venue> venueList, int day) {
        super(venueList);
        this.day = day;
        this.todoList = todoList;
    }

    public int getDay() {
        return day;
    }

    public List<Todo> getTodoList() {
        return this.todoList;
    }

    public void setDay(int day) {
        this.day = day;
    }

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
}
