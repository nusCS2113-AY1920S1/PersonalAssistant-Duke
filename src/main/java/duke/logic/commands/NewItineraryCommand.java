package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.locations.Venue;
import duke.model.planning.Itinerary;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

/**
 * Creates a new custom itinerary.
 */
public class NewItineraryCommand extends Command {
    private LocalDateTime start;
    private LocalDateTime end;
    private String hotel;
    private String name;
    private String[] itineraryDetails;

    /**
     * Constructs the command with the given sample itinerary.
     *
     */
    public NewItineraryCommand(LocalDateTime start, LocalDateTime end, String hotel, String name,
                               String[] itineraryDetails) {
        this.start = start;
        this.end = end;
        this.hotel = hotel;
        this.name = name;
        this.itineraryDetails = itineraryDetails;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException, FileNotFoundException {
        Venue hotelLocation = ApiParser.getLocationSearch(hotel);
        Itinerary itinerary = new Itinerary(start, end, hotelLocation, name);
        model.storeNewItinerary(itinerary, itineraryDetails);
        model.save();
        return new CommandResultText("New Itinerary Created with name:" + itinerary.getName());
    }
}
