package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.enumerations.Direction;
import sgtravel.commons.exceptions.NoSuchBusServiceException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.locations.BusStop;
import sgtravel.model.transports.BusService;

import java.util.HashMap;

/**
 * Retrieves the bus route of a given bus.
 */
public class GetBusRouteCommand extends Command {
    private String bus;

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
    public CommandResultText execute(Model model) throws NoSuchBusServiceException {
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

            return new CommandResultText(Messages.BUS_ROUTE_STARTER + result);
        } catch (NullPointerException e) {
            return new CommandResultText(Messages.BUS_ROUTE_NOT_FOUND);
        }
    }
}
