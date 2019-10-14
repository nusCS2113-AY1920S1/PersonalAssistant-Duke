package duke.commands;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import duke.data.BusStop;
import duke.logic.api.ApiParser;
import duke.storage.Storage;

import java.util.HashMap;

public class GetBusStopCommand extends Command {
    private String buscode;

    public GetBusStopCommand(String busCode) {
        this.buscode = busCode;
    }

    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        HashMap<String, BusStop> allBus = ApiParser.getBusStop();
        if (allBus.containsKey(this.buscode)) {
            return new CommandResult("This is the information for this Bus Stop:\n"
                    + allBus.get(this.buscode).getAddress() + "\n"
                    + allBus.get(this.buscode).getLatitude() + "\n"
                    + allBus.get(this.buscode).getLongitude());
        }
        throw new DukeException(Messages.DATA_NOT_FOUND);
    }

}
