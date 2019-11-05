package duke.logic.command;

import java.util.Optional;

import duke.exception.DukeException;
import duke.extensions.Pomodoro;
import duke.storage.Storage;
import duke.storage.UndoStack;
import duke.task.Task;
import duke.tasklist.TaskList;
import duke.ui.Ui;

public class PomodoroCommand extends Command {
    private String fullCommand;
    Optional<String> filter;

    /**
     * Constructor that takes in the command after "pomo"
     * @param command command to determine what pomodoro to start
     */
    public PomodoroCommand(Optional<String> filter, String command) {
        this.fullCommand = command;
        this.filter = filter;
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
        String[] fcArray = fullCommand.split(" ",2);
        String command = fcArray[0];
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
        case "assign":
            int indexNo;
            if (fcArray.length == 1) {
                throw new DukeException("Please input the index of the task you would like to assign!");
            }
            try {
                indexNo = Integer.parseInt(fcArray[1]) - 1;
            } catch (NumberFormatException e) {
                throw new DukeException("Please enter a numerical field for the index!");
            }
            Task t = tasks.get(filter, indexNo);
            pomodoro.setPomodoroTask(t);
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
