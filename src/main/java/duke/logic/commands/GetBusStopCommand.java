package duke.logic.commands;

import duke.logic.commands.results.CommandResultText;
import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.locations.BusStop;

import java.util.HashMap;

public class GetBusStopCommand extends Command {
    private String buscode;

    public GetBusStopCommand(String busCode) {
        this.buscode = busCode;
    }

    @Override
    public CommandResultText execute(Model model) throws DukeException {
        HashMap<String, BusStop> allBus = model.getMap().getBusStopMap();
        if (allBus.containsKey(this.buscode)) {
            return new CommandResultText("This is the information for this Bus Stop:\n"
                    + allBus.get(this.buscode).getAddress() + "\n"
                    + allBus.get(this.buscode).getLatitude() + "\n"
                    + allBus.get(this.buscode).getLongitude());
        }
        throw new DukeException(Messages.DATA_NOT_FOUND);
    }

}
