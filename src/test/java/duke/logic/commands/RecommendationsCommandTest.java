package duke.logic.commands;

import duke.ModelStub;
import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.locations.Venue;
import duke.model.planning.Itinerary;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecommendationsCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2019, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2019, 9, 13, 9, 9);
        Venue hotel = new Venue("YEW TEE INDUSTRIAL ESTATE", 1.3973210291170202,
                103.753758637401, 0, 0);

        Itinerary itinerary = new Itinerary(startDate,endDate,hotel);
        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itinerary);
        CommandResultText commandResult = recommendationsCommand.execute(model);
        String result1 = commandResult.getMessage();

        assertEquals(result1, itinerary.printItinerary().toString());
    }

}
