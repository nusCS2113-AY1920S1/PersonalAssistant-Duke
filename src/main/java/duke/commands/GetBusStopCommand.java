package duke.commands;

import duke.commons.DukeException;
import duke.data.BusStop;
import duke.parsers.api.ApiParser;
import duke.storage.Storage;
import duke.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class GetBusStopCommand extends Command {
    private String buscode;

    public GetBusStopCommand(String buscode) {
        this.buscode = buscode;
    }

    @Override
    public void execute(Ui ui, Storage storage) throws DukeException, IOException {
            ArrayList<BusStop> allBus = ApiParser.getBusStop();
            for (BusStop temp : allBus) {
                if (temp.getBusCode().equals(this.buscode)) {
                    ui.show("This is the information for this Bus Stop:\n"
                            + temp.getAddress() + "\n"
                            + temp.getLatitude() + "\n"
                            + temp.getLongitude());
                }
            }

    }

}
