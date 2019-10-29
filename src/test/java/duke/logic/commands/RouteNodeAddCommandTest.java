package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
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

        //test for ExceptionInInitializerError exception, as it is thrown as a result of the image generated
        //after the command has been executed
        assertThrows(ExceptionInInitializerError.class, () -> {
            RouteNodeAddCommand routeNodeAddCommand1 =
                    (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 1 at 66211 by bus");
            routeNodeAddCommand1.execute(model);
        });
        assertTrue(model.getRoutes().get(0).getNode(0) instanceof BusStop);

        BusStop busStop = new BusStop("66211", null, null, 0.0, 0.0);
        BusStop newBusStop = (BusStop) model.getRoutes().get(0).getNode(0);

        assertEquals(busStop.getBusCode(), newBusStop.getBusCode());

        //negative test for adding to non-existent route
        RouteNodeAddCommand routeNodeAddCommand2 =
                (RouteNodeAddCommand) Parser.parseComplexCommand("routeNodeAdd 2 at 66211 by bus");
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeNodeAddCommand2.execute(model);
        });
    }
}
