package storage.task;

import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


class Deadline extends Task {

    // Initialization
    Deadline(String name) throws DukeException {
        super(name);
        this.taskType = TaskType.DEADLINE;
        this.recordTaskDetails(name);
        this.parseDateTime();
    }

    private void parseDateTime() throws DukeException {

        if (this.detailDesc == null) {
            return;
        }
        if (this.detailDesc.equals("by")) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(this.taskDetails,
                        DateTimeFormatter.ofPattern("dd/MM/yy HHmm"));
                LocalDate localDate = dateTime.toLocalDate();
                LocalTime localTime = dateTime.toLocalTime();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                this.setDate(localDate);
                this.setTime(localTime);
            } catch (Exception e) {
                this.setDate(LocalDate.now());
                this.setTime(LocalTime.now());
                throw new DukeException("Invalid Input. Unable to interpret Datetime (use: dd/MM/yy HHmm) \n"
                        + "So we made the deadline for the task TODAY! I guess you are rushing \n!");
            }
        }
    }

    // -- Setters & Getters

    /**
     * Getter for datetime.
     * @return Datetime stored in this Deadline Object
     */
}
