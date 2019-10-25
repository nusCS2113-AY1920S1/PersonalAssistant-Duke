package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import javax.xml.stream.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocationSearchCommandTest {
    private static final String SENTOSA_MESSAGE
            = "These are the coordinates of your search:\n"
            + "SENTOSA\n"
            + "1.2498144130047 103.82948052356899";

    private static final String NUS_MESSAGE
            = "These are the coordinates of your search:\n"
            + "14 MEDICAL DRIVE NATIONAL UNIVERSITY OF SINGAPORE SINGAPORE 117599\n"
            + "1.29514416794657 10378118562806601";

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        //test for a regular search
        LocationSearchCommand locationSearchCommand1 =
                (LocationSearchCommand) Parser.parseComplexCommand("search sentosa");
        assertEquals(SENTOSA_MESSAGE, locationSearchCommand1.execute(model).getMessage());

        //test for a search with whitespaces
        LocationSearchCommand locationSearchCommand2 =
                (LocationSearchCommand) Parser.parseComplexCommand("search sentosa");
        assertEquals(SENTOSA_MESSAGE, locationSearchCommand2.execute(model).getMessage());

        //negative test where the api returns no result
        assertThrows(ApiNullRequestException.class, () -> {
            Parser.parseComplexCommand("search invalid location");
        });

        //negative test where the request times out
        assertThrows(ApiTimeoutException.class, () -> {
            Parser.parseComplexCommand("search #");
        });

    }
}
