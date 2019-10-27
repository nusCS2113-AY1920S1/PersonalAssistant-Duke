package seedu.hustler.command.schedulecommands;

import seedu.hustler.command.Command;
import seedu.hustler.ui.Ui;
import seedu.hustler.schedule.RecommendedSchedule;

/**
 * Changes the time allocated to an entry in the
 * recommended schedule.
 */
public class UpdateEntry extends Command {
    /**
     * User's input that needs to be parsed.
     */
    private String[] userInput;
    
    /**
     * Initialized the userInput with supplied value.
     *
     * @param userInput initialization value
     */
    public UpdateEntry(String[] userInput) {
        this.userInput = userInput;
    }
    
    /**
     * Changes the time allocated to a task in the recommended
     * schedule.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            String[] numbers = userInput[1].split(" ");
            int index = Integer.parseInt(numbers[0]);
            int time = this.parseDuration(numbers[1]); 
            index--;
            
            RecommendedSchedule.updateAllocTime(index, time);
            RecommendedSchedule.displayRecommendedSchedule();
        } catch (NumberFormatException e) {
            ui.numberCommandError();
        }
    }
    
    /**
     * Parses the date time supplied in the form of H:M:S
     * to seconds.
     *
     * @param datetime datetime duration to parse
     * @return parsed number of seconds
     */
    public int parseDuration(String datetime) {
        String[] times = datetime.split(":");
        int hours = Integer.parseInt(times[0]);
        int minutes = Integer.parseInt(times[1]);
        int seconds = Integer.parseInt(times[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }
}
