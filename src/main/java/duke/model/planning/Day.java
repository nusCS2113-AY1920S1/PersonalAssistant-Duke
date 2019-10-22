package duke.model.planning;

import duke.model.lists.VenueList;
import duke.model.locations.Venue;

import java.util.List;

public class Day extends VenueList {
    private List<Todo> todoList;
    private int number;

    /**
     * Constructor to initialise new Day object.
     */
    public Day(List<Todo> todoList, List<Venue> venueList, int number) {
        super();
        addList(venueList);
        this.number = number;
        this.todoList = todoList;
    }

    public int getNumber() {
        return number;
    }

    public List<Todo> getTodoList() {
        return this.todoList;
    }

    @Override
    public List<Venue> getVenueList() {
        return super.getVenueList();
    }

    public void setTodo(int index, Todo todo) {
        todoList.set(index,todo);
    }

    @Override
    public void setVenueList(Venue venue, int index) {
        super.setVenueList(venue, index);
    }

    public void setNumber(int newNum) {
        this.number = newNum;
    }
}
