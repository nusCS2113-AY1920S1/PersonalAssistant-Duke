package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.model.transports.Route;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteNodeListAllCommandTest {

    @Test
    void execute() throws FileLoadFailException, DuplicateRouteException {
        ModelStub model = new ModelStub();
        Route route1 = new Route("Go to NUS", "by SGTRavel");
        Route route2 = new Route("Go to MBS", "take the train");
        Route route3 = new Route("Go to Pulau Tekong", "I like the ferry");

        model.getRoutes().add(route1);
        model.getRoutes().add(route2);
        model.getRoutes().add(route3);

        String expected = "Here is the information of Routes:\n"
                + "There are 3 Routes.\n" + "\n" + "(1) Go to NUS\n" + "Description:\n" + "by SGTRavel\n"
                + "\n" + "(2) Go to MBS\n" + "Description:\n" + "take the train\n" + "\n" + "(3) Go to Pulau Tekong\n"
                + "Description:\n" + "I like the ferry\n";
        RouteListAllCommand command = new RouteListAllCommand();
        assertEquals(expected, command.execute(model).getMessage());
    }
}
