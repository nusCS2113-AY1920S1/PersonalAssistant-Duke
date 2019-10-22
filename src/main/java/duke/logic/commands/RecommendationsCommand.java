package duke.logic.commands;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.results.CommandResultText;
import duke.model.Model;
import duke.model.planning.Day;
import duke.model.planning.Itinerary;

import java.util.List;

/**
 * Class representing a command to list items in a task list.
 */
public class RecommendationsCommand extends Command {
    private Itinerary itinerary;

    public RecommendationsCommand(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {

        List<Day> list = model.getRecommendations();

        assert (!list.isEmpty()) : "list should not be null";

        itinerary.setTasks(list);

        StringBuilder result = itinerary.printItinerary();

        // Until more locations are added

        if (itinerary.getNumberOfDays() > 7) {
            throw new DukeException("Too many days, enter less than 8 ");
        }

        return new CommandResultText(result.toString());
    }
}
