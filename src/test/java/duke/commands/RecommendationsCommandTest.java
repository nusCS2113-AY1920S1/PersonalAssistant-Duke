package duke.commands;

import duke.ModelStub;
import duke.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.locations.Venue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecommendationsCommandTest {

    @Test
    void execute() throws DukeException {
        ModelStub model = new ModelStub();
        RecommendationsCommand recommendationsCommand = new RecommendationsCommand("5");
        CommandResultText commandResult = recommendationsCommand.execute(model);
        String result1 = commandResult.getMessage();

        List<Venue> list = model.getRecommendations();
        StringBuilder result = new StringBuilder("Here are the list of Recommended Locations in 5 days:\n");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                result.append("Day ").append((i / 2) + 1).append(":").append("\n");
            }
            result.append(i).append(". ").append(list.get(i).getAddress()).append("\n");
        }
        assertEquals(result1, result.toString());
    }

}
