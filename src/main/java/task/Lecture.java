package task;

import java.time.LocalDateTime;
import parser.DateTimeExtractor;

/**
 * This extension of the Event class will allow the student to add Lectures to
 * his schedule.
 *
 * @author Varun
 */
public class Lecture extends Event {

    public Lecture(String description, LocalDateTime toDate, LocalDateTime atDate) {
        super(description, toDate, atDate);
    }

    /**
     * This override of the toString function of the Event class etches the
     * different portions of the user input into a single string.
     *
     * @return This function returns a string of the required Lecture in the desired
     *         output format of string type.
     */
    @Override
    public String toString() {

        return "[LEC]" + "[" + super.getStatusIcon() + "]" + this.description + "(at: "
                + this.startDate.format(DateTimeExtractor.DATE_FORMATTER) + "-"
                + this.endDate.format(DateTimeExtractor.DATE_FORMATTER) + ")";
    }
}