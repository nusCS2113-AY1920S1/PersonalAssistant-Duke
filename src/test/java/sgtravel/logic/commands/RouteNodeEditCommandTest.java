package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.commons.exceptions.InputNotDoubleException;
import sgtravel.commons.exceptions.UnknownConstraintException;
import sgtravel.model.Model;

import sgtravel.model.locations.BusStop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteNodeEditCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand = new RouteAddCommand("2113", "");
        routeAddCommand.execute(model);

        BusStop newNode = new BusStop("66211", "", "", 0, 0);
        newNode.fetchData(model);
        model.getRoutes().get(0).add(newNode);
        assertTrue(model.getRoutes().get(0).getNode(0) instanceof BusStop);

        RouteNodeEditCommand routeNodeEditCommand1 = new RouteNodeEditCommand(0, 0, "address", "2113T");
        routeNodeEditCommand1.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getNode(0).getAddress());

        RouteNodeEditCommand routeNodeEditCommand2 = new RouteNodeEditCommand(0, 0, "description", "2113T");
        routeNodeEditCommand2.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getNode(0).getDescription());

        RouteNodeEditCommand routeNodeEditCommand3 = new RouteNodeEditCommand(0, 0, "type", "BUS");
        routeNodeEditCommand3.execute(model);
        assertEquals(Constraint.valueOf("BUS"), model.getRoutes().get(0).getNode(0).getType());

        //negative test for a constraint that does not exist
        RouteNodeEditCommand routeNodeEditCommand4 = new RouteNodeEditCommand(0, 0, "type", "2113T");
        assertThrows(UnknownConstraintException.class, () -> {
            routeNodeEditCommand4.execute(model);
        });

        RouteNodeEditCommand routeNodeEditCommand5 = new RouteNodeEditCommand(0, 0, "latitude", "2113");
        routeNodeEditCommand5.execute(model);
        assertEquals(2113, model.getRoutes().get(0).getNode(0).getLatitude());

        //negative test for latitude that cannot be converted to a Double
        RouteNodeEditCommand routeNodeEditCommand6 = new RouteNodeEditCommand(0, 0, "latitude", "NOT_DOUBLE");
        assertThrows(InputNotDoubleException.class, () -> {
            routeNodeEditCommand6.execute(model);
        });

        RouteNodeEditCommand routeNodeEditCommand7 = new RouteNodeEditCommand(0, 0, "longitude", "2113");
        routeNodeEditCommand7.execute(model);
        assertEquals(2113, model.getRoutes().get(0).getNode(0).getLongitude());

        //negative test for longitude that cannot be converted to a Double
        RouteNodeEditCommand routeNodeEditCommand8 = new RouteNodeEditCommand(0, 0, "longitude", "NOT_DOUBLE");
        assertThrows(InputNotDoubleException.class, () -> {
            routeNodeEditCommand8.execute(model);
        });
    }
}
