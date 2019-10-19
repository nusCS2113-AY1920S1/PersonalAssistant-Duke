package duke.commands;

import duke.commands.results.CommandResultText;
import duke.commons.exceptions.DukeException;
import duke.model.Model;
import duke.model.locations.Venue;

import java.util.List;

/**
 * Class representing a command to list items in a task list.
 */
public class RecommendationsCommand extends Command {
    private int days;

    public RecommendationsCommand(int days) {
        this.days = days;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     */
    @Override
    public CommandResultText execute(Model model) throws DukeException {
        List<Venue> list = model.getRecommendations();

        StringBuilder result = new StringBuilder("Here are the list of Recommended Locations in "
                + days + " days:\n");

        assert (!list.isEmpty()) : "list should not be null";

        for (int i = 0; i < 2 * days; i++) {
            if (i % 2 == 0) {
                result.append("Day ").append((i / 2) + 1).append(":").append("\n");
            }
            result.append(i).append(". ").append(list.get(i).getAddress()).append("\n");
        }

        // Until more locations are added

        if (days > 7) {
            throw new DukeException("Too many days, enter less than 8 ");
        }
        return new CommandResultText(result.toString());
    }
}
