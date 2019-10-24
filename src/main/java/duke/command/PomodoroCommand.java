package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;
import duke.extensions.Pomodoro;

import java.io.IOException;
import java.text.ParseException;

public class PomodoroCommand extends Command {
    String command;

    public PomodoroCommand (String command) {
        this.command = command;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, ParseException, DukeException {
        Pomodoro pomodoro = new Pomodoro();
        switch (command) {
            case "work":
                pomodoro.workTimer(ui);
                break;
            case "break":
                pomodoro.shortBreakTimer(ui);
                break;
            case "long break":
                pomodoro.longBreakTimer(ui);
                break;
            default:
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what field you are trying to edit!");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
