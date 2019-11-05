package duke.logic.commands;

import duke.commons.exceptions.NoSuchItineraryException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;

/**
 * Shows the requested Itinerary.
 */
public class ShowItineraryCommand extends Command {
    private String name;

    /**
     * Constructs the command with the given itinerary name.
     *
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
        return new CommandResultText(itinerary.printItinerary());
    }
}
