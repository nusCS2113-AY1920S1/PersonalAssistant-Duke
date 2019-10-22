package seedu.hustler.command.taskCommand;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.data.Schedule;
import java.util.Date;

/**
 * Command that lists tasks in TaskList instance.
 */
public class ScheduleCommand extends Command {
    /**
     * A schedule instance that shows schedules.
     */
    private Schedule schedule = new Schedule();
    
    /**
     * User input that contains the date.
     */
    private String[] userInput;

    /**
     * Initializes userInput.
     *
     * @param userInput the array of users inputs that contains date.
     */
    public ScheduleCommand(String[] userInput) {
        this.userInput = userInput;
    }
    
    /**
     * Lists commands which have the date specified in userInput.
     */
    public void execute() {
        if (this.userInput.length == 1) {
            Ui ui = new Ui();
            ui.empty_description_error();
        }
        Date date = schedule.convertStringToDate(this.userInput[1]);
        if (date != (null)) {
            schedule.printSchedule(date);
        }
    }
}
