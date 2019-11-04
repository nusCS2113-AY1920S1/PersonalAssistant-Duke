package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
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

        String[] itineraryDetails = {
            "23/04/15","24/04/15", "bedok", "SundayVacay", "1", "/venue", "Bedok",
            "/do", "swimming", "/and", "jumping", "/and", "swinging"
        };

        NewItineraryCommand newItineraryCommand = new NewItineraryCommand(startDate, endDate,
                "YEW TEE INDUSTRIAL ESTATE", "SundayPlan",itineraryDetails);

        newItineraryCommand.execute(model);

        assertTrue(model.getItineraryTable().containsKey("SundayPlan"));

    }

}
