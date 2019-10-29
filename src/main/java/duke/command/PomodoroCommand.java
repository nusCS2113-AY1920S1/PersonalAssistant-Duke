package duke.command;

import duke.exception.DukeException;
import duke.extensions.Pomodoro;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class PomodoroCommand extends Command {
    private String command;

    public PomodoroCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Pomodoro pomodoro = Pomodoro.getInstance();
        switch (command) {
        case "start":
            pomodoro.startTimer();
            break;
        case "status":
            pomodoro.getStatus();
            break;
        case "reset":
            pomodoro.resetState();
            break;
        case "restart":
            pomodoro.restartPomodoro();
            break;
        case "stop":
            pomodoro.stopTimer();
            break;
        default:
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what pomodoro you are referring to");
        }
    }

    /**
     * Not applicable for this Command.
     * @param tasks NA
     * @param undoStack NA
     * @throws DukeException NA
     */
    @Override
    public void savePrevState(TaskList tasks, UndoStack undoStack) throws DukeException {

    }
}
