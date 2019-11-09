package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.model.locations.BusStop;
import sgtravel.model.transports.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteShowCommandTest {
    @Test
    void execute() throws FileLoadFailException, DuplicateRouteException, OutOfBoundsException,
            DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("Test route", "2113");
        model.addRoute(route);

        RouteShowCommand command = new RouteShowCommand(0);
        command.execute(model);

        BusStop v1 = new BusStop("45039","Opp Yew Tee Ind Est", "Woodlands Rd",
                1.39585817355572, 103.75427816224409);

        model.getRoutes().get(0).addNode(v1);
        command = new RouteShowCommand(0);
        command.execute(model);

        //OOB tests
        RouteShowCommand command2 = new RouteShowCommand(-1);
        assertThrows(OutOfBoundsException.class, () -> {
            command2.execute(model);
        });

        RouteShowCommand command3 = new RouteShowCommand(-2);
        assertThrows(OutOfBoundsException.class, () -> {
            command3.execute(model);
        });

        RouteShowCommand command4 = new RouteShowCommand(1);
        assertThrows(OutOfBoundsException.class, () -> {
            command4.execute(model);
        });

        RouteShowCommand command5 = new RouteShowCommand(2);
        assertThrows(OutOfBoundsException.class, () -> {
            command5.execute(model);
        });
    }
}
