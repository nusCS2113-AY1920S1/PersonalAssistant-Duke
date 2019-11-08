package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.RepeatedDayNumberException;
import sgtravel.logic.api.ApiParser;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.NewItineraryCommand;
import sgtravel.logic.parsers.ParserTimeUtil;
import sgtravel.logic.parsers.storageparsers.PlanningStorageParser;
import sgtravel.model.locations.Venue;
import sgtravel.model.planning.Agenda;
import sgtravel.model.planning.Itinerary;
import sgtravel.model.planning.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Parses the user inputs into suitable format for CreateNewItineraryCommand.
 */
public class CreateNewItineraryParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;

    /**
     * Constructs the CreateNewItineraryParser.
     *
     * @param input The user input.
     */
    public CreateNewItineraryParser(String input) {
        this.input = input;
    }

    /**
     * This returns the list of agendas for a newly entered Itinerary.
     * @param itineraryDetails is the details of the itinerary to make.
     * @throws ParseException if the incorrect format is used in entering the itinerary.
     */
    public List<Agenda> getAgendaList(String[] itineraryDetails) throws ParseException {
        try {
            List<Agenda> agendaList = new ArrayList<>();
            Set<Integer> numbers = new HashSet<>();
            return makeAgendaList(itineraryDetails, agendaList, numbers);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_ITINERARY_FAIL_CREATION);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.ERROR_ITINERARY_INCORRECT_COMMAND);
        }

    }

    /**
     * This makes the list of agendas for a newly entered Itinerary.
     * @param itineraryDetails is the details of the itinerary to make.
     * @throws ParseException if the incorrect format is used in entering the itinerary.
     */
    private List<Agenda> makeAgendaList(String[] itineraryDetails, List<Agenda> agendaList, Set<Integer> numbers)
            throws ParseException {
        int i = 3;
        while (i < itineraryDetails.length) {
            HashMap<String, Venue> venueMap = new HashMap<>();
            List<Todo> todoList = new ArrayList<>();
            final int number = Integer.parseInt(itineraryDetails[i++]);
            while (itineraryDetails[i].equals("/venue")) {
                i++;
                venueMap.put(itineraryDetails[i], ApiParser.getLocationSearch(itineraryDetails[i]));
                i++;
                StringBuilder todos = new StringBuilder();
                if (i == itineraryDetails.length - 1 || itineraryDetails[i].matches("-?\\d+")) {
                    throw new ParseException(Messages.ERROR_ITINERARY_EMPTY_TODOLIST);
                }
                todos.append(itineraryDetails[++i]).append("|");
                i++;
                while (itineraryDetails[i].equals("/and")) {
                    i++;
                    todos.append(itineraryDetails[i++]).append("|");
                    if (i >= itineraryDetails.length) {
                        break;
                    }
                }
                todoList.addAll(PlanningStorageParser.getTodoListFromStorage(todos.toString()));
                if (i >= itineraryDetails.length) {
                    break;
                }
            }
            checkForRepeatedDayNumber(numbers, number);
            numbers.add(number);
            agendaList.add(getAgendaFromMap(venueMap, todoList, number));
        }
        return agendaList;
    }

    /**
     * Takes the VenueMap, todoList as well as number and returns an Agenda.
     * @param venueMap is the hash-map containing all the Venues for the day.
     * @param todoList is the list of todos for the day
     * @param number is the day number
     */
    private Agenda getAgendaFromMap(HashMap<String, Venue> venueMap, List<Todo> todoList, int number) {
        List<Venue> venueList = new ArrayList<>();
        for (Map.Entry<String, Venue> entry : venueMap.entrySet()) {
            venueList.add(entry.getValue());
        }
        return new Agenda(todoList, venueList, number);
    }

    /**
     * Checks if any duplicate day numbers have been entered.
     * @param numbers is the hash-map containing all the day numbers.
     * @param number is the current number to check
     * @throws RepeatedDayNumberException if a day number is repeated.
     */
    private void checkForRepeatedDayNumber(Set<Integer> numbers, int number) throws RepeatedDayNumberException {
        if(numbers.contains(number)) {
            throw new RepeatedDayNumberException();
        }
    }


    /**
     * Parses user input and constructs an NewItineraryCommand object.
     *
     * @return NewItineraryCommand object.
     * @throws ParseException If NewItineraryCommand object cannot be created from user input.
     */
    @Override
    public Command parse() throws ParseException {
        String[] itineraryDetails = input.substring("newItinerary".length()).strip().split(" ");

        LocalDateTime start = ParserTimeUtil.parseStringToDate(itineraryDetails[ZERO].strip());
        LocalDateTime end = ParserTimeUtil.parseStringToDate(itineraryDetails[ONE].strip());
        String name = itineraryDetails[TWO].strip();

        Itinerary itinerary = new Itinerary(start, end, name);

        itinerary.checkValidDate();

        List<Agenda> agendaList = getAgendaList(itineraryDetails);

        itinerary.setTasks(agendaList);

        return new NewItineraryCommand(itinerary);
    }
}
