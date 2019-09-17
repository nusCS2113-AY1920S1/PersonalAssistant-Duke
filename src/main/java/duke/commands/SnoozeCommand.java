package duke.commands;

import duke.exceptions.DukeException;
import duke.storage.Storage;
import duke.tasks.Schedule;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.ArrayList;

public class SnoozeCommand extends Command {
    private int day;
    private int hour;

    public SnoozeCommand(int day, int hour) {
        this.day = day;
        this.hour = hour;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage, Schedule schedule) throws DukeException {
        try {
            schedule.snooze(this.day, this.hour, ui);
        } catch (DukeException e) {
            throw new DukeException(e.getMessage());
        }
    }
}
