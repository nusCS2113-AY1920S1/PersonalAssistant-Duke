package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.ParseException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.ParserStorageUtil;
import duke.model.Model;
import duke.model.lists.AgendaList;
import duke.model.locations.Venue;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.planning.Todo;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a new custom itinerary.
 */
public class NewItineraryCommand extends Command {
    private LocalDateTime start;
    private LocalDateTime end;
    private String hotel;
    private String name;
    private String[] itineraryDetails;

    /**
     * Constructs the command with the given sample itinerary.
     *
     */
    public NewItineraryCommand(LocalDateTime start, LocalDateTime end, String hotel, String name,
                               String[] itineraryDetails) {
        this.start = start;
        this.end = end;
        this.hotel = hotel;
        this.name = name;
        this.itineraryDetails = itineraryDetails;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        Venue hotelLocation = ApiParser.getLocationSearch(hotel);
        Itinerary itinerary = new Itinerary(start, end, hotelLocation, name);
        AgendaList agendaList = new AgendaList();
        int i = 4;
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
                        throw new ParseException(Messages.ITINERARY_EMPTY_TODOLIST);
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
                    todoList = ParserStorageUtil.getTodoListFromStorage(todos.toString());
                    if (i >= itineraryDetails.length) {
                        break;
                    }
                }
                Agenda agenda = new Agenda(todoList, venueList, number);
                agendaList.add(agenda);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ITINERARY_FAIL_CREATION);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.ITINERARY_INCORRECT_COMMAND);
        }
        itinerary.setTasks(agendaList);


        model.saveItinerary(itinerary);
        model.itineraryListSave(itinerary);
        return new CommandResultText("New Itinerary Created :" + itinerary.printItinerary());
    }
}
