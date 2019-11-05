package duke.logic.parsers.storageparsers;

import duke.commons.Messages;
import duke.commons.exceptions.ParseException;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.Event;
import duke.model.locations.Venue;

import java.time.LocalDateTime;

/**
 * Storage parser for Event.
 */
public class EventStorageParser {
    /**
     * Parses a Event from String format back to Event.
     *
     * @param line The String description of an Event.
     * @return The corresponding Event object.
     * @throws ParseException If the data is corrupted.
     */
    public static Event createEventFromStorage(String line) throws ParseException {
        try {
            String[] eventParts = line.split("\\|");
            String type = eventParts[0].strip();
            String status = eventParts[1].strip();
            String description = eventParts[2].strip();
            Event event;
            assert ("E".equals(type)) : "There should only be events.";
            LocalDateTime start = ParserTimeUtil.parseStringToDate(eventParts[3].strip());
            LocalDateTime end = ParserTimeUtil.parseStringToDate(eventParts[4].strip());
            Venue location = getLocationFromStorage(eventParts);
            event = new Event(description, start, end, location);
            event.setDone("true".equals(status));
            return event;
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
        }
    }

    /**
     * Parses part of an Event back into a Venue object.
     * @param taskParts The parts of the Event.
     * @return The Venue of the Event.
     */
    private static Venue getLocationFromStorage(String[] taskParts) {
        String address = taskParts[5].strip();
        double latitude = Double.parseDouble(taskParts[6].strip());
        double longitude = Double.parseDouble(taskParts[7].strip());
        double distX = Double.parseDouble(taskParts[8].strip());
        double distY = Double.parseDouble(taskParts[9].strip());
        return new Venue(address, latitude, longitude, distX, distY);
    }

    /**
     * Parses an Event from Event to String format.
     *
     * @param event The Event.
     * @return The corresponding String format of the task object.
     */
    public static String toStorageString(Event event) {
        return "E | " + event.isDone() + " | " + event.getDescription() + " | " + event.getStartDate()
                + " | " + event.getEndDate() + " | " + event.getLocation();
    }
}
