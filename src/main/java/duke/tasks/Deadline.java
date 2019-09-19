package duke.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A class inheriting from duke.tasks.Task used to represent tasks that have both a description and an
 * associated deadline.
 */
public class Deadline extends Task {

    private String by;
    private LocalDateTime byLDT = null;

    /**
     * Constructor for a duke.tasks.Deadline task, which consists of the description of the task and the deadline
     * associated with it.
     *
     * The program assumes the following formats for date and time:
     *         dd/MM/yyyy HHmm
     *         dd/MM/yyyy hh:mm a
     *         dd/MM/yyyy          (time assumed as 2359)
     *                    HHmm
     *                    hh:mm a  (date assumed as today)
     *
     * @param description the description of the task
     * @param by the deadline associated with the task
     */
    public Deadline(String description, String by) {
        super(description);
        String[] simpleDateTime = by.trim().split(" ",2);
        try {
            LocalDateTime byDT = convertToLocalDateTime(simpleDateTime);
            this.byLDT = byDT;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
            this.by = byDT.format(dateTimeFormatter);
        } catch (Exception e) {
            this.by = by; // custom deadline
        }
    }

    @Override
    public String get_type() {
        return "D";
    }
    /**
     * Returns a String representation of the duke.tasks.Deadline object, displaying its type (duke.tasks.Deadline),
     * description and the deadline associated with it.
     *
     * @return a String representation of the duke.tasks.Deadline object
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Deciphers the date and time mentioned in the input and returns a LocalDateTime object
     * to be used for standardized date and time representation.
     *
     * @param simpleDateTime a String array that is the result of splicing the "by" section of the input command
     * @return the LocalDateTime object that is translated from the simple date and time keyed in as Strings
     */
    private LocalDateTime convertToLocalDateTime(String[] simpleDateTime) {

        LocalDateTime now = LocalDateTime.now();
        String defaultDate = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String defaultTime = "2359";

        if (simpleDateTime.length == 1) {
            String s = simpleDateTime[0];
            if (s.split(" ").length == 2) {
                // hh:mm a
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
                return LocalDateTime.parse(defaultDate + " " + s, formatter);
            } else if (s.length() == 4) {
                // HHmm
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                return LocalDateTime.parse(defaultDate + " " + s, formatter);
            } else {
                // dd/MM/yyyy
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                return LocalDateTime.parse(s + " " + defaultTime, formatter);
            }
        } else {
            String date = simpleDateTime[0];
            String time = simpleDateTime[1];
            DateTimeFormatter formatter;
            if (time.length() >= 7) {
                // dd/MM/yyyy hh:mm a
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
            } else {
                // dd/MM/yyyy HHmm
                formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            }
            return LocalDateTime.parse(date + " " + time, formatter);
        }
    }

    /**
     * Returns LocalDateTime object that corresponds to the assigned deadline to the task.
     *
     * @return deadline in the form of a LocalDateTime object
     */
    public LocalDateTime getByLDT() {
        return byLDT;
    }
}