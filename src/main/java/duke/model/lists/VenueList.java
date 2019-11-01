package duke.model.lists;

import duke.model.Event;
import duke.model.locations.Venue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a list of Venues (Locations).
 */
public class VenueList implements Iterable<Venue>, Listable<Venue> {
    private List<Venue> list;

    public VenueList() {
        list = new ArrayList<>();
    }

    /**
     * Alternative constructor given a list of tasks.
     */
    public VenueList(EventList events) {
        this();
        for (Event e : events) {
            list.add(e.getLocation());
        }
    }

    public VenueList(List<Venue> venueList) {
        list = venueList;
    }

    @Override
    public void add(Venue venue) {
        list.add(venue);
    }

    public List<Venue> getVenueList() {
        return this.list;
    }

    public void setVenue(int index, Venue venue) throws IndexOutOfBoundsException {
        list.set(index, venue);
    }

    @Override
    public Venue get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    public void remove(int index) throws IndexOutOfBoundsException {
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
