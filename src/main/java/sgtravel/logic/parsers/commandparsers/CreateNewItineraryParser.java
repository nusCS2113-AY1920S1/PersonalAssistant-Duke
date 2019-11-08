package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.commons.exceptions.ChronologyBeforePresentException;
import sgtravel.commons.exceptions.ChronologyInconsistentException;
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
import java.util.List;

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
     * This makes the list of agendas for a newly entered Itinerary.
     * @param itineraryDetails is the details of the itinerary to make.
     * @throws ParseException if the incorrect format is used in entering the itnerary.
     */
    public List<Agenda> makeAgendaList(String[] itineraryDetails) throws ParseException {
        List<Agenda> agendaList = new ArrayList<>();
        int i = 3;
        try {
            while (i < itineraryDetails.length) {
                List<Venue> venueList = new ArrayList<>();
                List<Todo> todoList = new ArrayList<>();
                final int number = Integer.parseInt(itineraryDetails[i++]);
                while (itineraryDetails[i].equals("/venue")) {
                    i++;
                    venueList.add(ApiParser.getLocationSearch(itineraryDetails[i++]));
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
                    todoList = PlanningStorageParser.getTodoListFromStorage(todos.toString());
                    if (i >= itineraryDetails.length) {
                        break;
                    }
                }
                Agenda agenda = new Agenda(todoList, venueList, number);
                agendaList.add(agenda);
            }
            return agendaList;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_ITINERARY_FAIL_CREATION);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.ERROR_ITINERARY_INCORRECT_COMMAND);
        }
    }

    /**
     * Checks if the dates entered by the user are valid.
     *
     * @throws ChronologyBeforePresentException If start and end date are in the past.
     * @throws ChronologyInconsistentException If start ad end date are invalid.
     */
    private void checkValidDate(LocalDateTime start, LocalDateTime end)
            throws ChronologyBeforePresentException, ChronologyInconsistentException {
        if (start.isBefore(LocalDateTime.now()) || end.isBefore(LocalDateTime.now())) {
            throw new ChronologyBeforePresentException();
        } else if (end.isBefore(start) || start.isAfter(end)) {
            throw new ChronologyInconsistentException();
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

        checkValidDate(start, end);

        Itinerary itinerary = new Itinerary(start, end, name);
        List<Agenda> agendaList = makeAgendaList(itineraryDetails);
        itinerary.setTasks(agendaList);

        return new NewItineraryCommand(itinerary);
    }
}
