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

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddSampleItineraryCommandTest {

    @Test
    void execute() throws DukeException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2019, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2019, 9, 10, 9, 9);
        Venue hotel = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202,
                103.753758637401, 0, 0);

        Itinerary itinerary = new Itinerary(startDate,endDate,hotel, "Test");
        List<Agenda> list = model.getRecommendations(itinerary.getNumberOfDays(), itinerary);
        itinerary.setTasks(list);
        AddSampleItineraryCommand addSampleItineraryCommand = new AddSampleItineraryCommand();
        addSampleItineraryCommand.execute(model);

        assertEquals(model.getItinerary("Test").getName(), itinerary.getName());

        File file = new File("testSamples.txt");
        file.delete();

    }

}
