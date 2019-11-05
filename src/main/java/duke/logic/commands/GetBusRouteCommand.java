package duke.logic.commands;

import duke.commons.Messages;
import duke.commons.enumerations.Direction;
import duke.logic.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.locations.BusStop;
import duke.model.transports.BusService;

import java.util.ArrayList;
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
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        try {
            assert (this.bus.matches("-?\\d+(\\.\\d+)?"));
            HashMap<String, BusService> busMap = model.getMap().getBusMap();
            BusService bus = busMap.get(this.bus);
            String result = "";

            HashMap<String, BusStop> allBus = model.getBusStops();
            for (String busCode : bus.getDirection(Direction.FORWARD)) {
                if (allBus.containsKey(busCode)) {
                    BusStop busStop = allBus.get(busCode);
                    result = result.concat(busCode + " " + busStop.getAddress() + "\n");
                }
            }

            return new CommandResultText(MESSAGE_BUS_ROUTE + result);

        } catch (Throwable e) {
            throw new DukeException(Messages.PROMPT_NOT_INT);
        }
    }
}
