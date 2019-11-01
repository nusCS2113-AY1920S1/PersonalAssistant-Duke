package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.model.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocationSearchCommandTest {
    private static final String SENTOSA_MESSAGE
            = "These are the coordinates of your search:\n"
            + "SENTOSA\n"
            + "1.2498144130047 103.82948052356899";

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        //test for a regular search
        LocationSearchCommand locationSearchCommand1 =
                (LocationSearchCommand) Parser.parseComplexCommand("search sentosa");
        assertEquals(SENTOSA_MESSAGE, locationSearchCommand1.execute(model).getMessage());

        //negative test where the api returns no result
        assertThrows(ApiException.class, () -> {
            Parser.parseComplexCommand("search invalid location");
        });

        //negative test where the request times out
        assertThrows(ApiException.class, () -> {
            Parser.parseComplexCommand("search #");
        });

    }
}
