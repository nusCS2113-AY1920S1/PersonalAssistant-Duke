package task;

import process.DukeException;

public class FixedTask extends Task {
    int durationHour, durationMinute;

    /**
     * Creates FixedTask object that represents a fixed duration task.
     * @param desc              Task description.
     * @param check             Mark task as done.
     * @param durationHour      Duration require to complete task. (Hours)
     * @param durationMinute    Duration require to complete task. (Minutes)
     * @throws DukeException
     */
    public FixedTask(String desc, boolean check, int durationHour, int durationMinute) throws DukeException {
        super(desc, check);
        checkHour(durationHour);
        this.durationHour = durationHour;
        checkMinute(durationMinute);
        this.durationMinute = durationMinute;
    }

    /**
     * Checks for valid hour representation.
     * @param hour              Integer hour to validate.
     * @throws DukeException    Hour is not a positive neutral number.
     */
    private void checkHour(int hour) throws DukeException {
        if (hour < 0) {
            throw new DukeException("FixedTask argument hour must be a positive neutral number.");
        }
    }

    /**
     * Checks for valid minute representation.
     * @param minute            Integer minute to validate.
     * @throws DukeException    Minute is not a positive neutral number less than 60.
     */
    private void checkMinute(int minute) throws DukeException {
        if (minute >= 60) {
            throw new DukeException("FixedTask argument minute can be simplified to hours.");
        }
        if (minute < 0) {
            throw new DukeException("FixedTask argument minute must be a positive neutral number.");
        }
    }

    /**
     * Formats object variables to text form.
     * @return  Printable text representation of object.
     */
    public String toString() {
        String printHour = durationHour > 0 ? durationHour + " Hour" + (durationHour > 1 ? "s" : "") : "";
        String printMinute = durationMinute > 0 ? durationMinute + " Minute" + (durationMinute > 1 ? "s" : "" ) : "";
        return "[F]" + super.toString() + " (Requires: " + printHour + (durationHour > 0 && durationMinute > 0 ? " " : "") + printMinute + ")";
    }
}
