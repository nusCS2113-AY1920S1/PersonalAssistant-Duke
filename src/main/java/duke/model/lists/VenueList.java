package duke.model.lists;

import duke.model.Event;
import duke.model.Task;
import duke.model.locations.Venue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VenueList implements Iterable<Venue> {
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

    public void add(Venue venue) {
        list.add(venue);
    }

    public List<Venue> getVenueList() {
        return this.list;
    }

    public void setVenueList(Venue venue, int index) {
        list.set(index,venue);
    }

    public void addList(List<Venue> venueList) {
        this.list = venueList;
    }

    public Venue get(int index) {
        return list.get(index);
    }

    public void remove(int index) {
        list.remove(index);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public boolean contains(Venue venue) {
        return list.contains(venue);
    }

    @Override
    public Iterator<Venue> iterator() {
        return list.iterator();
    }
}
