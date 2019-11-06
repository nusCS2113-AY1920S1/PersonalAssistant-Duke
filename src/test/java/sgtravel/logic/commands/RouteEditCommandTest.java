package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.DukeException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.commons.exceptions.UnknownFieldException;
import sgtravel.logic.parsers.Parser;
import sgtravel.model.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteEditCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        RouteEditCommand routeEditCommand1 =
                (RouteEditCommand) Parser.parseComplexCommand("routeEdit 1 name 2113T");
        routeEditCommand1.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getName());

        //negative test for editing non-existant route
        RouteEditCommand routeEditCommand2 =
                (RouteEditCommand) Parser.parseComplexCommand("routeEdit 2 name 2113T");
        assertThrows(OutOfBoundsException.class, () -> {
            routeEditCommand2.execute(model);
        });

        RouteEditCommand routeEditCommand3 =
                (RouteEditCommand) Parser.parseComplexCommand("routeEdit 1 description 2113T");
        routeEditCommand3.execute(model);
        assertEquals("2113T", model.getRoutes().get(0).getDescription());

        //negative test for editing non-existant field
        RouteEditCommand routeDeleteCommand4 =
                (RouteEditCommand) Parser.parseComplexCommand("routeEdit 1 test 2113T");
        assertThrows(UnknownFieldException.class, () -> {
            routeDeleteCommand4.execute(model);
        });
    }
}
