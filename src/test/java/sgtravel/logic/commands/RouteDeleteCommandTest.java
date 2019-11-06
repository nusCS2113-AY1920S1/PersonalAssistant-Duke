package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.parsers.Parser;
import sgtravel.model.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteDeleteCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteDeleteCommand routeDeleteCommand1 =
                (RouteDeleteCommand) Parser.parseComplexCommand("routeDelete 1");
        routeDeleteCommand1.execute(model);

        assertEquals(0, model.getRoutes().size());

        //negative test for deleting non-existant route
        RouteDeleteCommand routeDeleteCommand2 =
                (RouteDeleteCommand) Parser.parseComplexCommand("routeDelete 1");
        assertThrows(OutOfBoundsException.class, () -> {
            routeDeleteCommand2.execute(model);
        });
    }
}
