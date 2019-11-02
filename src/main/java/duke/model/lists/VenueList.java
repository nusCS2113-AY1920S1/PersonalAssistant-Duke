package duke.model.lists;

import duke.model.Event;
import duke.model.locations.Venue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a list of Venues (Locations) and contains its related accessor methods..
 */
public class VenueList implements Iterable<Venue>, Listable<Venue> {
    private List<Venue> list;

    public VenueList() {
        list = new ArrayList<>();
    }

    /**
     * Alternative constructor given a List of Venues.
     *
     * @param venueList The List of Venues.
     */
    public VenueList(List<Venue> venueList) {
        list = venueList;
    }

    /**
     * Alternative constructor given a list of events.
     *
     * @param events The EventListt.
     */
    public VenueList(EventList events) {
        this();
        for (Event e : events) {
            list.add(e.getLocation());
        }
    }

    /**
     * Adds a Venue to the VenueList.
     *
     * @param venue The Venue to add.
     */
    @Override
    public void add(Venue venue) {
        list.add(venue);
    }

    /**
     * Removes a Venue from the VenueList at a given index.
     *
     * @param index The index of the venue.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public void remove(int index) throws IndexOutOfBoundsException {
        list.remove(index);
    }

    /**
     * Gets the Venue at a given index.
     *
     * @param index The index to search for.
     * @return The Venue at the index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    @Override
    public Venue get(int index) throws IndexOutOfBoundsException {
        return list.get(index);
    }

    public List<Venue> getVenueList() {
        return this.list;
    }

    public void setVenue(int index, Venue venue) throws IndexOutOfBoundsException {
        list.set(index, venue);
    }

    /**
     * Returns if the VenueList is empty.
     *
     * @return true if the VenueList is empty.
     */
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Returns the size of the VenueList.
     *
     * @return The size of the VenueList.
     */
    @Override
    public int size() {
        return list.size();
    }

    /**
     * Returns if a given Venue is in the VenueList.
     *
     * @param venue The given Venue to check.
     * @return true If the Venue is already inside.
     */
    @Override
    public boolean contains(Venue venue) {
        return list.contains(venue);
    }

    /**
     * Returns an iterator to the List of Venues.
     *
     * @return The iterator to the List of Venues.
     */
    @Override
    public Iterator<Venue> iterator() {
        return list.iterator();
    }
}
