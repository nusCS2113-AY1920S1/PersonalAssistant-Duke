package mistermusik.logic;

import mistermusik.commons.events.eventtypes.Event;

/**
 * Exception that is thrown when a schedule clash is detected upon attempt to add new event.
 * Contains a reference to the clashing event to be shown to user.
 */
public class ClashException extends Exception {
    private Event clashEvent;

    ClashException(Event clashEvent) {
        this.clashEvent = clashEvent;
    }

    public Event getClashEvent() {
        return clashEvent;
    }
}
