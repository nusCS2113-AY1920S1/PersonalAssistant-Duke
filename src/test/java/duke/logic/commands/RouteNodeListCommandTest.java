package duke.logic.commands;

import duke.ModelStub;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.OutOfBoundsException;
import duke.logic.commands.results.CommandResultText;
import duke.logic.parsers.Parser;
import duke.model.Model;

import duke.model.locations.BusStop;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteNodeListCommandTest {

    @Test
    void execute() throws DukeException {
        Model model = new ModelStub();

        RouteAddCommand routeAddCommand =
                (RouteAddCommand) Parser.parseComplexCommand("routeAdd 2113");
        routeAddCommand.execute(model);

        BusStop newNode = new BusStop("66211", "", "", 0, 0);
        newNode.fetchData(model);
        model.getRoutes().get(0).add(newNode);

        RouteNodeListCommand routeNodeListCommand =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeList 1 1");
        CommandResultText result = routeNodeListCommand.execute(model);

        String expected = "Here is the information of the Bus Stop:\n66211\nOpp Bloxhome Dr\nBerwick Dr"
                + "\n(BUS, 1.36412138937997, 103.86103467229529)";
        assertEquals(expected, result.getMessage());

        //negative test for non-existing route
        RouteNodeListCommand routeNodeListCommand2 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeList 2 1");
        assertThrows(OutOfBoundsException.class, () -> {
            routeNodeListCommand2.execute(model);
        });

        //negative test for non-existing route node in existing route
        RouteNodeListCommand routeNodeListCommand3 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeList 1 2");
        assertThrows(OutOfBoundsException.class, () -> {
            routeNodeListCommand3.execute(model);
        });

        //negative test for negative values in non-existing route
        RouteNodeListCommand routeNodeListCommand4 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeList -2 1");
        assertThrows(OutOfBoundsException.class, () -> {
            routeNodeListCommand4.execute(model);
        });

        //negative test for negative values in non-existing route node in existing route
        RouteNodeListCommand routeNodeListCommand5 =
                (RouteNodeListCommand) Parser.parseComplexCommand("routeNodeList 1 -2");
        assertThrows(OutOfBoundsException.class, () -> {
            routeNodeListCommand5.execute(model);
        });
    }
}
