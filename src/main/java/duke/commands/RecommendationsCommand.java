package duke.commands;

import duke.model.ModelManager;
import duke.model.Venue;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a command to list items in a task list.
 */
public class RecommendationsCommand extends Command {
    public String days;

    public RecommendationsCommand(String days)
    {
        this.days = days;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) {
        ModelManager modelManager = new ModelManager();
        List<Venue> list = modelManager.getRecommendations();
        ui.showRecommendations(list, days);
    }
}
