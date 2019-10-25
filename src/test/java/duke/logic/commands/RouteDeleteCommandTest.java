package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.logic.parsers.Parser;
import duke.model.Model;
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
        assertThrows(QueryOutOfBoundsException.class, () -> {
            routeDeleteCommand2.execute(model);
        });
    }
}
