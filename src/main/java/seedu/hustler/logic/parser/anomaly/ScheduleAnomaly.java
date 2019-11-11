package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.ui.timer.statusTypes.threadStatus;

public class ScheduleAnomaly extends DetectAnomaly {

    /**
     * Detects anomaly in user input.
     *
     * @param userInput the index issued by the user
     */
    public void detect(String[] userInput) throws CommandLineException {
        try {
            if (!Hustler.timermanager.isRunning()) {
                throw new CommandLineException("Please start the time completion mode using the /timer command."); 
            }
        } catch (NumberFormatException e) {
            throw new CommandLineException("Please issue a number after the command: /command <index>");
        } catch (IndexOutOfBoundsException e) {
            throw new CommandLineException("Please enter the correct index of task.");
        }
    } 
}
