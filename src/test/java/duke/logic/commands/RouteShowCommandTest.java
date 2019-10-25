package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteShowCommandTest {

    @Test
    void execute() throws DukeException {
        String expected = "Here is the information of the Route:\n2113\n";

        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteListCommand routeListCommand1 =
                (RouteListCommand) Parser.parseComplexCommand("routeShow 1");
        CommandResultText result = routeListCommand1.execute(model);

        assertEquals(expected, result.getMessage());

        //negative test for non-existing route
        RouteListCommand routeListCommand2 =
                (RouteListCommand) Parser.parseComplexCommand("routeShow 2");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeListCommand2.execute(model);
        });

        //negative test for index 0
        RouteListCommand routeListCommand3 =
                (RouteListCommand) Parser.parseComplexCommand("routeShow 0");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeListCommand3.execute(model);
        });

        //negative test for negative index
        RouteListCommand routeListCommand4 =
                (RouteListCommand) Parser.parseComplexCommand("routeShow -1");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeListCommand4.execute(model);
        });
    }
}
