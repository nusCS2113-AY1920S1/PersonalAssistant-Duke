package mistermusik.logic;

import mistermusik.commons.events.eventtypes.Event;

public class ClashException extends Exception {
    private Event clashEvent;

    ClashException(Event clashEvent) {
        this.clashEvent = clashEvent;
    }

    public Event getClashEvent() {
        return clashEvent;
    }
}
