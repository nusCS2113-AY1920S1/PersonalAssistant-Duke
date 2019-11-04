package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.enumerations.Direction;
import duke.commons.exceptions.NoSuchBusServiceException;
import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.transports.BusService;

import java.util.HashMap;

/**
 * Retrieves the bus route of a given bus.
 */
public class GetBusRouteCommand extends Command {
    private String bus;
    private static final String MESSAGE_BUS_ROUTE = "Here is the bus route:\n";

    public GetBusRouteCommand(String bus) {
        this.bus = bus;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing transports.
     * @throws NoSuchBusServiceException If there is no such bus service.
     */
    @Override
    public CommandResultText execute(Model model) throws NoSuchBusServiceException{
        assert (this.bus.matches("-?\\d+(\\.\\d+)?"));
        HashMap<String, BusService> busMap = model.getMap().getBusMap();
        BusService bus = busMap.get(this.bus);
        if (bus == null) {
            throw new NoSuchBusServiceException();
        }
        String result = "";
        for (String busCode : bus.getDirection(Direction.FORWARD)) {
            result = result.concat(busCode + "\n");
        }
        return new CommandResultText(MESSAGE_BUS_ROUTE + result);
    }
}
