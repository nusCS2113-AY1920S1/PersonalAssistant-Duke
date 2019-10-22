package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.ui.timer.timerManager;

/**
 * Command that starts the timer.
 */
public class TimerCommand extends Command {
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Initializes taskInfo.
     * @param taskInfo the info of the task to add.
     */
    public TimerCommand(String[] taskInfo) {
        this.taskInfo = taskInfo;
    }

    /**
     * Starts the timer.
     */
    public void execute() {
        if (this.taskInfo.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }

        timerManager timermanager = new timerManager();
        timermanager.setTimer(taskInfo[1]);
        timermanager.startTimer();
    }
}
