package duke.commands;

import duke.commons.DukeException;
import duke.storage.Storage;
import duke.tasks.Task;
import duke.ui.Ui;
import javafx.collections.transformation.SortedList;

public class FreeTimeCommand extends Command {
    private int duration;

    public FreeTimeCommand(int duration) {
        this.duration = duration;
    }
    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        SortedList<Task> tasks = storage.getTasks().getChronoList();
        //tasks.add(0, )
        //tasks.add(tasks.size(), )
    }
}
