package executor.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


class Deadline extends Task {

    // Initialization
    Deadline(String name) {
        super(name);
        this.taskType = TaskType.DEADLINE;
        this.recordTaskDetails(name);
        this.parseDateTime();

    }

    private void parseDateTime() {

        if (this.detailDesc == null) {
            return;
        }
        if (this.detailDesc.equals("by")) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(this.taskDetails, DateTimeFormatter.ofPattern("dd-mm-yy HH:mm"));
                LocalDate localDate = dateTime.toLocalDate();
                LocalTime localTime = dateTime.toLocalTime();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                this.setDate(localDate);
                this.setTime(localTime);
                System.out.println("Date Interpreted: "
                        + getDate().format(dateFormatter)
                        +getTime().format(timeFormatter));
            } catch (Exception e) {
                this.setDate(LocalDate.now());
                this.setTime(LocalTime.now());
                System.out.println("Invalid Input. Unable to interpret Datetime (use: dd-mm-yy HH:mm)");
            }
        }
    }

    // -- Setters & Getters

    /**
     * Getter for datetime.
     * @return Datetime stored in this Deadline Object
     */
}
