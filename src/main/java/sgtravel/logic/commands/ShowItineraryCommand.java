package sgtravel.logic.commands;

import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Shows the requested Itinerary.
 */
public class ShowItineraryCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String name;

    /**
     * Constructs a ShowItineraryCommand with the requested names.
     * @param name The name of the itinerary to be shown.
     */
    public ShowItineraryCommand(String name) {
        this.name = name;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws NoSuchItineraryException If the itinerary cannot be found.
     */
    @Override
    public CommandResultText execute(Model model) throws NoSuchItineraryException {
        Itinerary itinerary = model.getItinerary(name);
        if (itinerary == null) {
            throw new NoSuchItineraryException();
        }
        logger.log(Level.FINE, "Itinerary Found!");
        return new CommandResultText(itinerary.printItinerary());
    }
}
