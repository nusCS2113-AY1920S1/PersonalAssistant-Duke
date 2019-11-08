package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.logic.parsers.Parser;
import sgtravel.model.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationSearchCommandTest {
    private static final String SENTOSA_MESSAGE
            = "These are the coordinates of your search:\n"
            + "SENTOSA\n"
            + "1.2498144130047 103.82948052356899";

    private static final String TIMEOUT_MESSAGE = "Sorry, but the search has timed out due to connection issues.";

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        //test for a regular search
        LocationSearchCommand locationSearchCommand1 =
                (LocationSearchCommand) Parser.parseComplexCommand("search sentosa");
        assertEquals(SENTOSA_MESSAGE, locationSearchCommand1.execute(model).getMessage());

        //not a valid location, will timeout
        LocationSearchCommand locationSearchCommand2 =
                (LocationSearchCommand) Parser.parseComplexCommand("search invalid-location");
        assertEquals(TIMEOUT_MESSAGE, locationSearchCommand2.execute(model).getMessage());

        //random nonsense query, will timeout
        LocationSearchCommand locationSearchCommand3 =
                (LocationSearchCommand) Parser.parseComplexCommand("search #");
        assertEquals(TIMEOUT_MESSAGE, locationSearchCommand3.execute(model).getMessage());
    }
}
