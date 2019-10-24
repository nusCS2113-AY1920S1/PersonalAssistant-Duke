package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteDeleteCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteDeleteCommand routeDeleteCommand =
                (RouteDeleteCommand) Parser.parseComplexCommand("routeDelete 1");
        routeDeleteCommand.execute(model);

        assertEquals(0, model.getRoutes().size());
    }
}
