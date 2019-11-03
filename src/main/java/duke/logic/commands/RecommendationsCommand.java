package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.model.planning.Recommendation;

/**
 * Recommends an itinerary based on number of trip days entered by user.
 */
public class RecommendationsCommand extends Command {
    // private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private String[] itineraryDetails;

    public RecommendationsCommand(String... itineraryDetails) {
        this.itineraryDetails = itineraryDetails;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {

        Recommendation recommendation = model.getRecommendations();

        Itinerary recentItinerary = recommendation.makeItinerary(itineraryDetails);

        model.storeRecentItinerary(recentItinerary);

        return new CommandResultText(recentItinerary.printItinerary());
    }
}
