package duke.logic.commands;

import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.ParseException;
import duke.logic.api.ApiParser;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.locations.Venue;
import duke.model.planning.Itinerary;

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
     * Constructs a NewItineraryCommand.
     * @param start The start date.
     * @param end The end date.
     * @param hotel The hotel name.
     * @param name The name of the itinerary.
     * @param itineraryDetails The details of the itinerary.
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
     * @throws ApiException If the api request fails.
     * @throws ParseException If the information cannot be parsed into an itinerary.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws ApiException, ParseException, FileNotSavedException {
        Venue hotelLocation = ApiParser.getLocationSearch(hotel);
        Itinerary itinerary = new Itinerary(start, end, hotelLocation, name);
        itinerary.getNumberOfDays();
        itinerary.makeAgendaList(itineraryDetails);
        model.setNewItinerary(itinerary);
        model.save();
        return new CommandResultText("New Itinerary Created with name:" + itinerary.getName());
    }
}
