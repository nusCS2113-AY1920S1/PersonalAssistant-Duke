package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecommendationsCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 13, 9, 9);

        String [] itineraryDetails = {"YEW TEE INDUSTRIAL ESTATE", startDate.toString(), endDate.toString()};
        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itineraryDetails);

        CommandResultText commandResult = recommendationsCommand.execute(model);
        String result1 = commandResult.getMessage();

        assertEquals(result1, model.getRecentItinerary().printItinerary());

    }
}
