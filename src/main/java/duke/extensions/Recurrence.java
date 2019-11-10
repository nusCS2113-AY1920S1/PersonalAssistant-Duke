package duke.extensions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import duke.exception.DukeException;

/**
 * Class that contains the logic of the recurrence of the task
 */
public class Recurrence {
    private LocalDateTime lastUpdatedDate;
    private RecurrencePeriod recurrencePeriod;

    /**
     * Constructor of the Recurrence class
     * @param recurrencePeriod an optional string of the recurrence period to set
     * @throws DukeException if invalid recurrence period inputted
     */
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

    public String recurrenceCode() {
        return recurrencePeriod.recurrenceCode();
    }

    public String recurrenceIcon() {
        return recurrencePeriod.recurrenceIcon();
    }

    /**
     * Method that checks whether it's time to reset the task to undone based on recurrence period
     * @return true if it is time to reset, false if it is not
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

