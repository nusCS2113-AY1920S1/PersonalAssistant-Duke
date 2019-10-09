package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.data.BusService;
import duke.parsers.api.ApiParser;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.HashMap;

public class GetBusRouteCommand extends Command {
    private String bus;

    public GetBusRouteCommand(String bus) {
        this.bus = bus;
    }

    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        HashMap<String, BusService> busMap = ApiParser.getBusRoute();
        BusService bus = busMap.get(this.bus);
        for (String busCode : bus.getDirection(1)) {
            ui.show(busCode);
        }
    }
}
