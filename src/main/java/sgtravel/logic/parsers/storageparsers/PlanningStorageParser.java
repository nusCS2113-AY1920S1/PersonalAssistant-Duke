package sgtravel.logic.parsers.storageparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.AddListFailException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.model.locations.Venue;
import sgtravel.model.planning.Agenda;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Todo;

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
            double latitude = Double.parseDouble(taskParts[1].strip());
            double longitude = Double.parseDouble(taskParts[2].strip());
            double distX = Double.parseDouble(taskParts[3].strip());
            double distY = Double.parseDouble(taskParts[4].strip());
            return new Venue(address, latitude, longitude, distX, distY);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
        }
    }

    /**
     * Parses a String into a todo list.
     * @param line The string containing the todo list.
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

    /**
     * Returns the new name of a recently added recommendation.
     * @param line The string containing the new name.
     * @return result The new name of the recommendation.
     */
    public static String getNewAddListName(String line) throws AddListFailException {
        String[] todoParts = line.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < todoParts.length; i++) {
            if (i == todoParts.length - 1) {
                stringBuilder.append(todoParts[i]);
            } else {
                stringBuilder.append(todoParts[i]).append(" ");
            }
        }

        String result = stringBuilder.toString();

        return result;
    }

    /**
     * Converts the itinerary into a storage friendly string format.
     *
     * @return itineraryString The itinerary object converted to a storage string
     */
    public static String toItineraryStorageString(Itinerary value) {
        StringBuilder itineraryString = new StringBuilder();
        itineraryString.append(value.getName()).append("\n").append(value.getStartDate().toString()).append("\n")
                .append(value.getEndDate().toString()).append("\n");
        for (Agenda agenda : value.getList()) {
            itineraryString.append(agenda.toString());
        }
        itineraryString.append("\n");
        return itineraryString.toString();
    }
}
