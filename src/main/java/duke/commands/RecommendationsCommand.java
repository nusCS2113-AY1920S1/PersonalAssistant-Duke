package duke.commands;

import duke.model.ModelManager;
import duke.model.Venue;
import duke.storage.Storage;

import java.util.List;

/**
 * Class representing a command to list items in a task list.
 */
public class RecommendationsCommand extends Command {
    public String days;

    public RecommendationsCommand(String days) {
        this.days = days;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param storage The storage object containing task list.
     */
    @Override
    public CommandResult execute(Storage storage) {
        ModelManager modelManager = new ModelManager();
        List<Venue> list = modelManager.getRecommendations();
        StringBuilder result = new StringBuilder("Here are the list of Recommended Locations in "
                + days + " days:\n");
        int i = 1;
        int j = Integer.parseInt(days);
        if (j <= 1) {
            j = 1;
        } else if (j <= 3) {
            j = 3;
        } else if (j <= 5) {
            j = 5;
        } else {
            j = list.size();
        }
        for (Venue t : list) {
            result.append(i).append(". ").append(t.getAddress()).append("\n");
            i += 1;
            if (i >= j) {
                break;
            }
        }
        return new CommandResult(result.toString());
    }
}
