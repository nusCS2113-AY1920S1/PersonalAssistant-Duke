package duke.model.lists;

import duke.commons.exceptions.DukeDuplicateTaskException;
import duke.model.Event;
import duke.model.TaskWithDates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a list of Events and contains its related accessor methods.
 */
public class EventList implements Iterable<Event>, Listable<Event>, Serializable {
    private List<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }

    /**
     * Alternative constructor given a List of Events.
     *
     * @param events The List of Events.
     */
    private EventList(List<Event> events) {
        this.events = events;
    }

    /**
     * Adds an Event to the VenueList.
     *
     * @param event The Event to add.
     */
    @Override
    public void add(Event event) throws DukeDuplicateTaskException {
        if (contains(event)) {
            throw new DukeDuplicateTaskException();
        }
        events.add(event);
    }

    /**
     * Removes a Event from the VenueList at a given index.
     *
     * @param index The index of the Event.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public Event remove(int index) {
        return events.remove(index);
    }

    /**
     * Sorts the List of Events based on date.
     */
    public void sort() {
        events.sort(Comparator.comparing(TaskWithDates::getStartDate)
                .thenComparing(TaskWithDates::getEndDate));
    }

    /**
     * Gets the Event at a given index.
     *
     * @param index The index to search for.
     * @return The Event at the index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Override
    public Event get(int index) throws IndexOutOfBoundsException {
        return events.get(index);
    }

    /**
     * Returns a shallow copy of the sorted EventList.
     */
    public EventList getSortedList() {
        return new EventList(events.stream().sorted(
                Comparator.comparing(TaskWithDates::getStartDate)
                        .thenComparing(TaskWithDates::getEndDate)).collect(Collectors.toList()));
    }

    /**
     * Replaces the contents of this list with {@code Events}.
     * {@code Events} must not contain duplicate Events.
     */
    public void setEvents(List<Event> events) throws DukeDuplicateTaskException {
        if (!eventsAreUnique(events)) {
            throw new DukeDuplicateTaskException();
        }
        this.events = events;
    }

    /**
     * Returns if the EventList is empty.
     *
     * @return true if the EventList is empty.
     */
    @Override
    public boolean isEmpty() {
        return events.isEmpty();
    }

    /**
     * Returns the size of the EventList.
     *
     * @return The size of the EventList.
     */
    @Override
    public int size() {
        return events.size();
    }

    /**
     * Returns if a given Event is in the EventList.
     *
     * @param event The given Event to check.
     * @return true If the Event is already inside.
     */
    @Override
    public boolean contains(Event event) {
        return events.stream().anyMatch(event::isSameTask);
    }

    /**
     * Returns true if all Events in list are unique.
     *
     * @param events The Events to check.
     * @return true If the Events are unique.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameTask(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if all Events are unique.
     *
     * @return true If all Events are unique.
     */
    public boolean isUnique() {
        return eventsAreUnique(events);
    }

    /**
     * Returns an iterator to the list of events.
     *
     * @return The iterator to the list of events.
     */
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }

}
