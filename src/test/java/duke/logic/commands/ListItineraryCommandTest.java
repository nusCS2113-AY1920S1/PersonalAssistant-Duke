package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.ParserStorageUtil;
import duke.model.lists.AgendaList;
import duke.model.locations.Venue;
import duke.model.planning.Agenda;
import duke.model.planning.Itinerary;
import duke.model.planning.Todo;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListItineraryCommandTest {

    @Test
    void execute() throws DukeException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2019, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2019, 9, 10, 9, 9);
        Venue hotel = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202,
                103.753758637401, 0, 0);
        Venue simei_green_condominium_ = new Venue("SIMEI GREEN CONDOMINIUM ", 1.34092338509908,
                103.95793706096599, 41868.7033958798, 35898.3641898744);
        Venue bedokville = new Venue("BEDOKVILLE", 1.32368201682055,
                103.947789319297, 40739.4553195806, 33991.8430694323);
        List<Venue> venueList = new ArrayList<>();
        venueList.add(simei_green_condominium_);
        venueList.add(bedokville);
        List<Todo> todoList = new ArrayList<>(
                ParserStorageUtil.getTodoListFromStorage("playball|sing|dance"));
        AgendaList agendaList = new AgendaList();
        Agenda agenda = new Agenda(todoList, venueList, 1);
        agendaList.add(agenda);
        Itinerary itinerary = new Itinerary(startDate,endDate,hotel, "Test");
        itinerary.setTasks(agendaList);

        model.itineraryListSave(itinerary);

        ListItineraryCommand listItineraryCommand = new ListItineraryCommand();
        CommandResultText commandResult = listItineraryCommand.execute(model);

        assertTrue(commandResult.getMessage().contains(itinerary.getName()));
    }
}
