package sgtravel.logic.commands;

import sgtravel.commons.exceptions.NoSuchBusStopException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.locations.BusStop;

import java.util.HashMap;

/**
 * Retrieves all the bus stops.
 */
public class GetBusStopCommand extends Command {
    private String buscode;

    /**
     * Creates a new GetBusStopCommand with the given bus stop number.
     *
     * @param busCode The bus stop number.
     */
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
     *
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
     * Gets the information of a Bus Stop.
     *
     * @param busStop The Bus Stop.
     * @return result The information of the Bus Stop.
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
