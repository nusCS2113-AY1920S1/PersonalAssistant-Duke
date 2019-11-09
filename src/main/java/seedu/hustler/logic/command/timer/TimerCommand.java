package seedu.hustler.logic.command.timer;

import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.TimerAnomaly;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.Scheduler;
import seedu.hustler.ui.timer.TimerManager;

/**
 * Command that starts the timer.
 */
public class TimerCommand extends Command {
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Detects anomalies for input.
     **/
    private TimerAnomaly anomaly = new TimerAnomaly();

    /**
     * Initializes taskInfo.
     * @param taskInfo the info of the task to add.
     */
    public TimerCommand(String[] taskInfo) {
        this.taskInfo = taskInfo;
    }
    
    /**
     * Starts the timer, initializes the recommended schedule
     * and displays the recommended schedule.
     */
    public void execute() {

        Ui ui = new Ui();

        try {
            anomaly.detect(taskInfo);
            String[] timerSplit = taskInfo[1].split(" ");
            int hours = Integer.parseInt(timerSplit[0]);
            int minutes = Integer.parseInt(timerSplit[1]);
            int seconds = Integer.parseInt(timerSplit[2]);
            Scheduler.recommend(hours * 3600 + minutes * 60 + seconds);
            Scheduler.displayRecommendedSchedule();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
            return;
        }

        TimerManager timermanager = new TimerManager();
        timermanager.setTimer(taskInfo[1]);
        timermanager.startTimer();
    }
}
