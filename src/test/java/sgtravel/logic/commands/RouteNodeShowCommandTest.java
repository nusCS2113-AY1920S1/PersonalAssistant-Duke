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

class RouteNodeShowCommandTest {

    @Test
    void execute() throws FileLoadFailException, DuplicateRouteException, QueryFailedException,
            DuplicateRouteNodeException {
        ModelStub model = new ModelStub();
        Route route = new Route("To Clementi", "by bus!");
        BusStop busStop = new BusStop("17009", model);
        route.addNode(busStop);
        model.addRoute(route);

        //NoClassDefFoundError thrown when image is created
        RouteNodeShowCommand command = new RouteNodeShowCommand(0, 0);
        assertThrows(NoClassDefFoundError.class, () -> {
            command.execute(model);
        });

        //OOB tests
        RouteNodeShowCommand command2 = new RouteNodeShowCommand(1, 0);
        assertThrows(OutOfBoundsException.class, () -> {
            command2.execute(model);
        });

        RouteNodeShowCommand command3 = new RouteNodeShowCommand(-1, 0);
        assertThrows(OutOfBoundsException.class, () -> {
            command3.execute(model);
        });

        RouteNodeShowCommand command4 = new RouteNodeShowCommand(0, -1);
        assertThrows(OutOfBoundsException.class, () -> {
            command4.execute(model);
        });

        RouteNodeShowCommand command5 = new RouteNodeShowCommand(0, 1);
        assertThrows(OutOfBoundsException.class, () -> {
            command5.execute(model);
        });

        TrainStation trainStation = new TrainStation("Ang Mo Kio", model);
        model.getRoutes().get(0).addNode(trainStation);

        //NoClassDefFoundError thrown when image is created
        RouteNodeShowCommand command6 = new RouteNodeShowCommand(0, 1);
        assertThrows(NoClassDefFoundError.class, () -> {
            command6.execute(model);
        });

        //edit first node such that is cannot be searched
        model.getRoutes().get(0).getNode(0).setAddress("somewhere_not_in_singapore");
        assertThrows(NoClassDefFoundError.class, () -> {
            command.execute(model);
        });

    }
}
