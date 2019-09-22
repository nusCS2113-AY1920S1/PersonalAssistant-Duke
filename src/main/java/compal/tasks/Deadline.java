package compal.tasks;

import compal.main.Duke;

import java.util.Date;

public class Deadline extends Task {

    /**
     * Deadline refers to assignment type task with a due date to be done by.
     *
     * @param description Description of the deadline to be stored
     * @param date        of the event to be stored
     */
    public Deadline(String description, String date) {
        super(description);
        super.symbol = "D";
        super.setDate(date);
    }


}
