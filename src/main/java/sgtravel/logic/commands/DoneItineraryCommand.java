package sgtravel.logic.commands;

import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Shows the requested Itinerary.
 */
public class DoneItineraryCommand extends Command {
    private String name;

    /**
     * Constructs the command with the given itinerary name.
     * @param name The name of the itinerary to mark as done (delete).
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
    public CommandResultText execute(Model model) throws NoSuchItineraryException, FileNotSavedException {
        model.doneItinerary(name);
        model.save();
        return new CommandResultText("Successfully deleted your itinerary with name " + name);
    }
}
