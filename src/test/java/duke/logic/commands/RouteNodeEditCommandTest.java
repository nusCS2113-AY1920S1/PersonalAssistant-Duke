package duke.logic.commands;

import duke.ModelStub;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteNodeEditCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand = new RouteAddCommand("2113");
        routeAddCommand.execute(model);

        RouteNodeAddCommand routeNodeAddCommand1 =
                (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 1 at 2113T by bus");
        routeNodeAddCommand1.execute(model);

        RouteNodeEditCommand routeNodeEditCommand1 = new RouteNodeEditCommand(0, 0, "address", "2113T");
        routeNodeEditCommand1.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getNode(0).getAddress());

        RouteNodeEditCommand routeNodeEditCommand2 = new RouteNodeEditCommand(0, 0, "description", "2113T");
        routeNodeEditCommand2.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getNode(0).getDescription());

        RouteNodeEditCommand routeNodeEditCommand3 = new RouteNodeEditCommand(0, 0, "type", "BUS");
        routeNodeEditCommand3.execute(model);
        assertEquals(Constraint.valueOf("BUS"), model.getRoutes().get(0).getNode(0).getType());

        RouteNodeEditCommand routeNodeEditCommand4 = new RouteNodeEditCommand(0, 0, "latitude", "2113");
        routeNodeEditCommand4.execute(model);
        assertEquals(2113, model.getRoutes().get(0).getNode(0).getLatitude());

        RouteNodeEditCommand routeNodeEditCommand5 = new RouteNodeEditCommand(0, 0, "longitude", "2113");
        routeNodeEditCommand5.execute(model);
        assertEquals(2113, model.getRoutes().get(0).getNode(0).getLongitude());
    }
}
