package task;

import process.DukeException;

public class FixedTask extends Task {
    int duration;

    /**
     * Creates FixedTask object that represents a fixed duration task.
     *
     * @param desc     Task description.
     * @param check    Mark task as done.
     * @param duration Duration require to complete task. (Minutes)
     * @throws DukeException
     */
    public FixedTask(String desc, boolean check, int duration) throws DukeException {
        super(desc, check);
        checkDuration(duration);
        this.duration = duration;
        super.tt = "F";
        super.extra = String.valueOf(duration);
    }

    /**
     * Checks for valid minute representation.
     *
     * @param minute Integer minute to validate.
     * @throws DukeException Minute is not a positive neutral number less than 60.
     */
    private void checkDuration(int minute) throws DukeException {
        if (minute < 1) {
            throw new DukeException("FixedTask argument duration must be a positive neutral number.");
        }
    }

    /**
     * Converts minutes into hours without decimal.
     * @param duration Minutes to be converted.
     * @return  Minutes in hours.
     */
    private int getHour(int duration) {
        return duration / 60;
    }

    /**
     * Converts minutes into hours and minutes. Return only remaining minutes.
     * @param duration Minutes to be converted.
     * @return  Remaining minutes after conversion.
     */
    private int getMinute(int duration) {
        return duration % 60;
    }

    /**
     * Formats object variables to text form.
     *
     * @return Printable text representation of object.
     */
    public String toString() {
        int hour = getHour(duration);
        int minute = getMinute(duration);
        String printHour = hour > 0 ? hour + " Hour" + (hour > 1 ? "s" : "") : "";
        String printMinute = minute > 0 ? minute + " Minute" + (minute > 1 ? "s" : "") : "";
        return "[F]" + super.toString() + " (Requires: " + printHour + (hour > 0 && minute > 0 ? " " : "") + printMinute + ")";
    }
}
