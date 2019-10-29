package duke.logic.commands;

import duke.commons.exceptions.DukeDateTimeParseException;
import duke.commons.exceptions.DukeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ItineraryInsufficientAgendas;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.storage.Storage;

import java.io.FileNotFoundException;

/**
 * Adds the given recommended list to users itineraries.
 */
public class AddSampleItineraryCommand extends Command {
    private Itinerary itinerary;

    /**
     * Constructs the command with the given sample itinerary.
     *
     * @throws DukeDateTimeParseException If the datetime cannot be parsed.
     * @throws FileLoadFailException If the file cannot be loaded.
     */
    public AddSampleItineraryCommand() throws DukeDateTimeParseException, FileLoadFailException {
        this.itinerary = null;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        // Add to the list of Itineraries
        this.itinerary = model.readRecommendations();
        model.itineraryListSave(this.itinerary);
        model.saveItinerary(this.itinerary);
        return new CommandResultText("Successfully added this itinerary: " + "\n"
                + itinerary.printItinerary());
    }
}
