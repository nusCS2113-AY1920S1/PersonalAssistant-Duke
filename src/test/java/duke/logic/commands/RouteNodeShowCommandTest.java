package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        //routeNodeShow 1 1
        RouteNodeListCommand routeNodeListCommand =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeShow 1 1");
        CommandResultText result = routeNodeListCommand.execute(model);

        assertEquals(expected, result.getMessage());
    }
}
