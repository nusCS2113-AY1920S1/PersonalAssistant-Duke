package duke.extensions;

import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Recurrence {
    RecurrencePeriod recurrencePeriod;
    protected LocalDateTime lastUpdatedDate;

    public Recurrence(Optional<String> recurrencePeriod) throws DukeException {
        this.lastUpdatedDate = LocalDateTime.now();
        if (recurrencePeriod.isPresent()) {
            if ("daily".equals(recurrencePeriod.get())) {
                this.recurrencePeriod = RecurrencePeriod.DAILY;
            } else if ("weekly".equals(recurrencePeriod.get())) {
                this.recurrencePeriod = RecurrencePeriod.WEEKLY;
            } else {
                throw new DukeException("Please enter a valid recurrence period!");
            }
        } else {
            this.recurrencePeriod = RecurrencePeriod.NONE;
        }
    }

    public String recurrenceDescription() {
        return recurrencePeriod.recurrenceDescription();
    }

    public String recurrenceCode() {
        return recurrencePeriod.recurrenceCode();
    }

    public String recurrenceIcon() {
        return recurrencePeriod.recurrenceIcon();
    }

    /**
     * This function marks tasks as undone every week/day based on the
     * recurrence period of the task.
     */
    public boolean isTimeToReset() {
        LocalDateTime dateNow = LocalDateTime.now();
        switch (recurrencePeriod) {
            case DAILY:
                if (ChronoUnit.DAYS.between(lastUpdatedDate, dateNow) > 0) {

                    lastUpdatedDate = LocalDateTime.now();
                    return true;
                } else {
                    System.out.println("not time to reset");
                    return false;
                }
            case WEEKLY:
                if (ChronoUnit.DAYS.between(lastUpdatedDate, dateNow) > 7) {
                    lastUpdatedDate = LocalDateTime.now();
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
}

