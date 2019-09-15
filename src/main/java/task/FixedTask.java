package task;

import process.DukeException;

public class FixedTask extends Task {
    int durationHour, durationMinute;

    public FixedTask(String desc, boolean check, int durationHour, int durationMinute) throws DukeException {
        super(desc, check);
        checkHour(durationHour);
        this.durationHour = durationHour;
        checkMinute(durationMinute);
        this.durationMinute = durationMinute;
    }

    private void checkHour(int hour) throws DukeException {
        if (hour < 0) {
            throw new DukeException("FixedTask argument hour must be a positive neutral number.");
        }
    }

    private void checkMinute(int minute) throws DukeException {
        if (minute >= 60) {
            throw new DukeException("FixedTask argument minute can be simplified to hours.");
        }
        if (minute < 0) {
            throw new DukeException("FixedTask argument minute must be a positive neutral number.");
        }
    }

    public String toString() {
        String printHour = durationHour > 0 ? durationHour + " Hour" + (durationHour > 1 ? "s" : "") : "";
        String printMinute = durationMinute > 0 ? durationMinute + " Minute" + (durationMinute > 1 ? "s" : "" ) : "";
        return "[F]" + super.toString() + " (Requires: " + printHour + (durationHour > 0 && durationMinute > 0 ? " " : "") + printMinute + ")";
    }
}
