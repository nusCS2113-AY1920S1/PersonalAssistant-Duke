package duke.model.lists;

import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.events.TaskWithDates;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EventList implements Iterable<Event>, Listable<Event> {
    private List<Event> events;

    public EventList() {
        events = new ArrayList<>();
    }

    /**
     * Constructs an EventList.
     * Creates a copy of the events in tasks.
     * @param tasks The tasks to extract events from.
     */
    public EventList(TaskList tasks) {
        this();
        for (Task t : tasks.getEventList()) {
            assert (t instanceof Event) : "getEventList should filter out only Events";
            events.add((Event) t);
        }
    }

    @Override
    public void add(Event e) {
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
    public Event get(int index) {
        return events.get(index);
    }

    public void sort() {
        events.sort(Comparator.comparing(TaskWithDates::getStartDate));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
