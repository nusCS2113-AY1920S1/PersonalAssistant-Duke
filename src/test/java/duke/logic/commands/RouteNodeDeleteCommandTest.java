package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteNodeDeleteCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteNodeAddCommand routeNodeAddCommand =
                (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 1 at 2113T by bus");
        routeNodeAddCommand.execute(model);

        RouteNodeDeleteCommand routeNodeDeleteCommand =
                (RouteNodeDeleteCommand) Parser.parseComplexCommand("routeNodeDelete 1 1");
        routeNodeDeleteCommand.execute(model);

        assertTrue(model.getRoutes().get(0).getNumNodes() == 0);

        RouteDeleteCommand routeDeleteCommand =
                (RouteDeleteCommand) Parser.parseComplexCommand("routeDelete 1");
        routeDeleteCommand.execute(model);
    }
}
