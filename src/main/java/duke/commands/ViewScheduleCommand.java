package duke.commands;

import java.util.ArrayList;
import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Schedule;
import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.tasks.Task;

public class ViewScheduleCommand extends Command {
    private String date;

    public ViewScheduleCommand(String date) {
        this.date = date;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Schedule schedule) throws DukeException {
        ArrayList<Task> temp = schedule.viewSchedule(this.date);
        ui.showSchedule(temp, date);
    }
}
