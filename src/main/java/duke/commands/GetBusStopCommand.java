package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.data.BusStop;
import duke.parsers.api.ApiParser;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class GetBusStopCommand extends Command {
    private String buscode;

    public GetBusStopCommand(String busCode) {
        this.buscode = busCode;
    }

    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        HashMap<String, BusStop> allBus = ApiParser.getBusStop();
        if (allBus.containsKey(this.buscode)) {
            ui.show("This is the information for this Bus Stop:\n"
                    + allBus.get(this.buscode).getAddress() + "\n"
                    + allBus.get(this.buscode).getLatitude() + "\n"
                    + allBus.get(this.buscode).getLongitude());
        }
    }

}
