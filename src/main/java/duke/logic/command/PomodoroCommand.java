package duke.logic.command;

import duke.exception.DukeException;
import duke.extensions.Pomodoro;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class PomodoroCommand extends Command {
    private String command;

    /**
     * Constructor that takes in the command after "pomo"
     * @param command command to determine what pomodoro to start
     */
    public PomodoroCommand(String command) {
        this.command = command;
    }

    /**
     * Execute function override. Takes in command after pomo and determines what pomodoro function to execute
     *
     * @param tasks
     * @param ui
     * @param storage
     * @throws DukeException if command not understood
     */
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
        case "answer":
            String answer = pomodoro.getAnswer();
            ui.showLine(answer);
            break;
        default:
            throw new DukeException("I'm sorry, but I don't know what pomodoro you are referring to");
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
