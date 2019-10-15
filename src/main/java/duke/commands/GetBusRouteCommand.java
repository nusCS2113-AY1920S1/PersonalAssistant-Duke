package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.transports.BusService;

import java.util.HashMap;

public class GetBusRouteCommand extends Command {
    private String bus;
    private static final String MESSAGE_BUS_ROUTE = "Here is the bus route:\n";

    public GetBusRouteCommand(String bus) {
        this.bus = bus;
    }

    @Override
    public CommandResult execute(Model model) throws DukeException {
        HashMap<String, BusService> busMap = model.getMap().getBusMap();
        BusService bus = busMap.get(this.bus);
        String result = "";
        for (String busCode : bus.getDirection(1)) {
            result = result.concat(busCode + "\n");
        }
        return new CommandResult(MESSAGE_BUS_ROUTE + result);
    }
}
