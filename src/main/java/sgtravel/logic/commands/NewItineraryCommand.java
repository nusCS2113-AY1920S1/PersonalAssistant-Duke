package sgtravel.logic.commands;

import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

import java.time.LocalDateTime;

/**
 * Creates a new custom itinerary.
 */
public class NewItineraryCommand extends Command {
    private Itinerary itinerary;

    /**
     * Constructs a NewItineraryCommand with the users details.
     * @param itinerary The itinerary to add.
     */
    public NewItineraryCommand(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws ApiException If the api request fails.
     * @throws ParseException If the information cannot be parsed into an itinerary.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws ApiException, ParseException, FileNotSavedException {
        model.setNewItinerary(itinerary);
        model.save();
        return new CommandResultText("New Itinerary Created with name:" + itinerary.getName());
    }
}
