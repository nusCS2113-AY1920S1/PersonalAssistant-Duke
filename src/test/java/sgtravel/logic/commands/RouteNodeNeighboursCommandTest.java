package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.model.locations.BusStop;
import sgtravel.model.locations.TrainStation;
import sgtravel.model.transports.Route;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteNodeNeighboursCommandTest {

    @Test
    void execute() throws FileLoadFailException, QueryFailedException, DuplicateRouteException,
            DuplicateRouteNodeException, OutOfBoundsException {
        ModelStub model = new ModelStub();
        Route route = new Route("To Clementi", "by bus!");
        BusStop busStop = new BusStop("17009", model);
        route.add(busStop);
        model.addRoute(route);

        RouteNodeNeighboursCommand command1 = new RouteNodeNeighboursCommand(0, 0);
        assertThrows(NoClassDefFoundError.class, () -> {
            command1.execute(model);
        });

        //OOB tests
        String expectedError = "I'm sorry, but it is out of bounds...\n";
        RouteNodeNeighboursCommand command2 = new RouteNodeNeighboursCommand(1, 0);
        assertEquals(expectedError, command2.execute(model).getMessage());

        RouteNodeNeighboursCommand command3 = new RouteNodeNeighboursCommand(-1, 0);
        assertEquals(expectedError, command3.execute(model).getMessage());

        RouteNodeNeighboursCommand command4 = new RouteNodeNeighboursCommand(0, 1);
        assertEquals(expectedError, command4.execute(model).getMessage());

        RouteNodeNeighboursCommand command5 = new RouteNodeNeighboursCommand(0, -1);
        assertEquals(expectedError, command5.execute(model).getMessage());

        TrainStation trainStation = new TrainStation("Ang Mo Kio", model);
        model.getRoutes().get(0).add(trainStation);

        RouteNodeNeighboursCommand command6 = new RouteNodeNeighboursCommand(0, 1);
        assertThrows(NoClassDefFoundError.class, () -> {
            command6.execute(model);
        });
    }
}
