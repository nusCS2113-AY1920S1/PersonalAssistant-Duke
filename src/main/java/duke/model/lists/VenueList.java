package duke.model.lists;

import duke.model.events.Event;
import duke.model.events.Task;
import duke.model.locations.Venue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VenueList implements Iterable<Venue>, Listable<Venue> {
    private List<Venue> list;

    public VenueList() {
        list = new ArrayList<>();
    }

    /**
     * Alternative constructor given a list of tasks.
     */
    public VenueList(List<Task> tasks) {
        this();
        for (Task t : tasks) {
            if (t instanceof Event) {
                list.add(((Event) t).getLocation());
            }
        }
    }

    @Override
    public void add(Venue venue) {
        list.add(venue);
    }

    @Override
    public Venue get(int index) {
        return list.get(index);
    }

    public void remove(int index) {
        list.remove(index);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Venue venue) {
        return list.contains(venue);
    }

    @Override
    public Iterator<Venue> iterator() {
        return list.iterator();
    }
}
