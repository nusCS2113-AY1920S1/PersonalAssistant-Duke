package duke.logic.parsers.storageParsers;

import duke.commons.Messages;
import duke.commons.exceptions.ParseException;
import duke.model.locations.Venue;
import duke.model.planning.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Storage parser for plannings.
 */
public class PlanningStorageParser {
    /**
     * Parses a String into a Venue object.
     * @param line The String input.
     * @return The Venue object.
     * @throws ParseException If the data is corrupted.
     */
    public static Venue getVenueFromStorage(String line) throws ParseException {
        try {
            String[] taskParts = line.split("\\|");
            String address = taskParts[0].strip();
            double longitude = Double.parseDouble(taskParts[1].strip());
            double latitude = Double.parseDouble(taskParts[2].strip());
            double distX = Double.parseDouble(taskParts[3].strip());
            double distY = Double.parseDouble(taskParts[4].strip());
            return new Venue(address, latitude, longitude, distX, distY);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
        }
    }

    /**
     * Returns a list of todo's from a text file.
     *
     * @return The List of todo's.
     */
    public static List<Todo> getTodoListFromStorage(String line) {
        List<Todo> todoList = new ArrayList<>();
        String[] todoParts = line.split("\\|");
        for (String todoPart : todoParts) {
            Todo todo = new Todo(todoPart);
            todoList.add(todo);
        }
        return todoList;
    }
}
