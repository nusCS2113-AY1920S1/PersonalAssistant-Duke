package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShowItineraryCommandTest {

    @Test
    void execute() throws DukeException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 13, 9, 9);

        String [] itineraryDetails = {"YEW TEE INDUSTRIAL ESTATE", startDate.toString(), endDate.toString()};

        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itineraryDetails);

        recommendationsCommand.execute(model);

        AddSampleItineraryCommand addSampleItineraryCommand = new AddSampleItineraryCommand("New List");

        addSampleItineraryCommand.execute(model);

        ShowItineraryCommand showItineraryCommand = new ShowItineraryCommand(model.getRecentItinerary().getName());

        CommandResultText commandResultText = showItineraryCommand.execute(model);

        String result = commandResultText.getMessage();

        assertEquals(result, model.getItinerary(model.getRecentItinerary().getName()).printItinerary());

    }

}
