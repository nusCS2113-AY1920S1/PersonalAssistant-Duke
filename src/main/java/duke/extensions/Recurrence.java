package duke.extensions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import duke.exception.DukeException;

public class Recurrence {
    private LocalDateTime lastUpdatedDate;
    private RecurrencePeriod recurrencePeriod;

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

