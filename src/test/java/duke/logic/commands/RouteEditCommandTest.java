package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.model.Model;
import duke.model.transports.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteEditCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteEditCommand routeEditCommand1 =
                (RouteEditCommand) Parser.parseComplexCommand("routeEdit 1 name 2113T");
        routeEditCommand1.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getName());

        RouteEditCommand routeEditCommand2 =
                (RouteEditCommand) Parser.parseComplexCommand("routeEdit 1 description 2113T");
        routeEditCommand2.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getDescription());
    }
}
