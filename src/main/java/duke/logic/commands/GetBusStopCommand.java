package duke.logic.commands;

import duke.commons.exceptions.NoSuchBusStopException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.locations.BusStop;

import java.util.HashMap;

/**
 * Retrieves all the bus stops.
 */
public class GetBusStopCommand extends Command {
    private String buscode;

    public GetBusStopCommand(String busCode) {
        this.buscode = busCode;
    }

    /**
     * Executes this command and returns a text result.
     *
     * @param model The model object containing transports.
     * @throws NoSuchBusStopException If no such bus stop exists.
     */
    @Override
    public CommandResultText execute(Model model) throws NoSuchBusStopException {
        HashMap<String, BusStop> allBus = model.getBusStops();
        return new CommandResultText(getResult(allBus));
    }

    /**
     * Gets the result of the bus stop query.
     * @param allBus Hash map that stores all bus stops in Singapore.
     * @return The result of the query in String.
     * @throws NoSuchBusStopException If no such bus stop exists.
     */
    private String getResult(HashMap<String, BusStop> allBus) throws NoSuchBusStopException {
        if (allBus.containsKey(buscode)) {
            BusStop busStop = allBus.get(buscode);
            return getBusStopInformation(busStop);
        }
        throw new NoSuchBusStopException();
    }

    /**
     * Gets the information of a bus stop.
     */
    private String getBusStopInformation(BusStop busStop) {
        String result = "This is the information for this Bus Stop:\n"
                + busStop.getAddress() + "\n";

        for (String busCode : busStop.getBuses()) {
            result += busCode + "\n";
        }

        return result;
    }
}
