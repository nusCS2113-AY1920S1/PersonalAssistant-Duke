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

class RouteNodeShowCommandTest {

    @Test
    void execute() throws DukeException {
        String expected = "Here is the information of the Bus Stop:\n2113T\nnull\nnull\n(BUS, 0.0, 0.0)";
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteNodeAddCommand routeNodeAddCommand =
                (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 1 at 2113T by bus");
        routeNodeAddCommand.execute(model);

        RouteNodeListCommand routeNodeListCommand =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeShow 1 1");
        CommandResultText result = routeNodeListCommand.execute(model);

        assertEquals(expected, result.getMessage());

        //negative test for non-existing route
        RouteNodeListCommand routeNodeListCommand2 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeShow 2 1");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeListCommand2.execute(model);
        });

        //negative test for non-existing route node in existing route
        RouteNodeListCommand routeNodeListCommand3 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeShow 1 2");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeListCommand3.execute(model);
        });

        //negative test for negative values in non-existing route
        RouteNodeListCommand routeNodeListCommand4 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeShow -2 1");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeListCommand4.execute(model);
        });

        //negative test for negative values in non-existing route node in existing route
        RouteNodeListCommand routeNodeListCommand5 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeShow 1 -2");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeListCommand5.execute(model);
        });
    }
}
