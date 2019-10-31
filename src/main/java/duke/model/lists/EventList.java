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

    private EventList(List<Event> events) {
        this.events = events;
    }

    @Override
    public void add(Event e) throws DukeDuplicateTaskException {
        if (contains(e)) {
            throw new DukeDuplicateTaskException();
        }
        events.add(e);
    }

    @Override
    public boolean isEmpty() {
        return events.isEmpty();
    }

    @Override
    public boolean contains(Event toCheck) {
        return events.stream().anyMatch(toCheck::isSameTask);
    }

    @Override
    public int size() {
        return events.size();
    }

    @Override
    public Event get(int index) throws IndexOutOfBoundsException {
        return events.get(index);
    }

    public void sort() {
        events.sort(Comparator.comparing(TaskWithDates::getStartDate)
                .thenComparing(TaskWithDates::getEndDate));
    }

    /**
     * Returns a shallow copy of the sorted EventList.
     */
    public EventList getSortedList() {
        return new EventList(events.stream().sorted(
                Comparator.comparing(TaskWithDates::getStartDate)
                        .thenComparing(TaskWithDates::getEndDate)).collect(Collectors.toList()));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
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
     * Returns true if {@code Events} contains only unique Events.
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

    public boolean isUnique() {
        return eventsAreUnique(events);
    }

    public Event remove(int index) {
        return events.remove(index);
    }
}
