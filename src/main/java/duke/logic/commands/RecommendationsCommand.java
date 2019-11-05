package duke.logic.commands;

import duke.commons.exceptions.ParseException;
import duke.commons.exceptions.RecommendationFailException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Itinerary;
import duke.model.planning.Recommendation;

/**
 * Recommends an itinerary based on number of trip days entered by user.
 */
public class RecommendationsCommand extends Command {
    private String[] itineraryDetails;

    public RecommendationsCommand(String... itineraryDetails) {
        this.itineraryDetails = itineraryDetails;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws ParseException If the itineraryDetails cannot be parsed.
     * @throws RecommendationFailException If there are too many days.
     */
    @Override
    public CommandResultText execute(Model model) throws ParseException, RecommendationFailException {

        Recommendation recommendation = model.getRecommendations();

        Itinerary recentItinerary = recommendation.makeItinerary(itineraryDetails);

        model.setRecentItinerary(recentItinerary);

        return new CommandResultText(recentItinerary.printItinerary());
    }
}
