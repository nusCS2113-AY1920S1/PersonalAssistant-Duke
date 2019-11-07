package sgtravel.logic.commands;

import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.commons.exceptions.OutOfBoundsException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

/**
 * Shows the requested Itinerary.
 */
public class DoneItineraryCommand extends Command {
    private String name;

    /**
     * Constructs the command with the given itinerary name.
     *
     */
    public DoneItineraryCommand(String name) {
        this.name = name;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws NoSuchItineraryException If the itinerary cannot be found.
     */
    @Override
    public CommandResultText execute(Model model) throws NoSuchItineraryException, OutOfBoundsException {
        model.doneItinerary(name);
        return new CommandResultText("Successfully deleted your itinerary with name " + name);
    }
}
