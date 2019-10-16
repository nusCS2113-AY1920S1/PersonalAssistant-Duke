package task;

import java.time.LocalDateTime;
import parser.DateTimeExtractor;

/**
 * This extension of the Event class will allow the student to add Assignments to
 * his schedule.
 *
 * @author Varun
 */
public class Assignment extends Deadline {

    public Assignment(String description, LocalDateTime atDate) {
        super(description, atDate);
    }

    /**
     * This override of the toString function of the Event class etches the
     * different portions of the user input into a single string.
     *
     * @return This function returns a string of the required Assignment in the desired
     *         output format of string type.
     */
    @Override
    public String toString() {
        return "[A'MENT]" + "[" + super.getStatusIcon() + "]" + this.description + "(by: "
                + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }
}