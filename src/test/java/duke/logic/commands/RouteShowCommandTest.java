package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteShowCommandTest {

    @Test
    void execute() throws DukeException {
        String expected = "Here is the information of the Route:\n2113\n";

        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteListCommand routeListCommand =
                (RouteListCommand) Parser.parseComplexCommand("routeShow 1");
        CommandResultText result = routeListCommand.execute(model);

        assertEquals(expected, result.getMessage());
    }
}
