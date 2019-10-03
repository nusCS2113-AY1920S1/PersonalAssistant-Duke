package eggventory;

import eggventory.items.Event;
import java.util.Comparator;

public class EventDateTimeComparator implements Comparator<Event> {
    public int compare(Event first, Event second) {
        return first.getEventStartTimeObj().getAt().compareTo(second.getEventStartTimeObj().getAt());
    }
}
