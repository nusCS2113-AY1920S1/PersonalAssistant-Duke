package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NewItineraryCommandTest {

    @Test
    void execute() throws DukeException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 13, 9, 9);

        String [] itineraryDetails = {"itinerary ", startDate.toString(), endDate.toString()};
        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itineraryDetails);

        recommendationsCommand.execute(model);

        NewItineraryCommand newItineraryCommand = new NewItineraryCommand(model.getRecentItinerary());

        newItineraryCommand.execute(model);

        assertTrue(model.getItineraryTable().containsKey("New Recommendation"));

    }

}
