package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Schedule;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public class TentativeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Schedule schedule) throws DukeException {
        schedule.tentative();
    }
}
