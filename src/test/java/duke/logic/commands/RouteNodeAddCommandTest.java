package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.logic.parsers.Parser;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.transports.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RouteNodeAddCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        Route route = new Route("2113", null);

        model.getRoutes().add(route);

        RouteNodeAddCommand routeNodeAddCommand1 =
                (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 1 at 2113T by bus");
        routeNodeAddCommand1.execute(model);
        assertTrue(model.getRoutes().get(0).getNode(0) instanceof BusStop);

        BusStop busStop = new BusStop("2113T", null, null, 0.0, 0.0);
        BusStop newBusStop = (BusStop) model.getRoutes().get(0).getNode(0);

        assertEquals(busStop.getBusCode(), newBusStop.getBusCode());

        //negative test for adding to non-existant route
        RouteNodeAddCommand routeNodeAddCommand2 =
                (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 2 at 2113T by bus");
        assertThrows(IndexOutOfBoundsException.class, () -> {
            routeNodeAddCommand2.execute(model);
        });
    }
}
