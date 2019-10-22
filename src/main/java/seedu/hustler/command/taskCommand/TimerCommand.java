package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.ui.timer.timerManager;
import seedu.hustler.schedule.RecommendedSchedule;
import seedu.hustler.schedule.Scheduler;

public class TimerCommand extends Command {
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Initializes taskInfo.
     *
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
        if (this.taskInfo.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
            return;
        }

        String[] timerSplit = taskInfo[1].split(" ");
        int hours = Integer.parseInt(timerSplit[0]);
        int minutes = Integer.parseInt(timerSplit[1]);
        int seconds = Integer.parseInt(timerSplit[2]);
        RecommendedSchedule.recommend(hours * 3600 + minutes * 60 + seconds);
        RecommendedSchedule.displayRecommendedSchedule();

        timerManager timermanager = new timerManager();
        timermanager.setTimer(taskInfo[1]);
        timermanager.startTimer();
    }
}
