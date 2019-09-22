package compal.tasks;

import compal.main.Duke;

import java.util.Date;

public class DoAfterTasks extends Task {

    /**
     * DoAfterTask Constructor.
     *
     * @param description task description
     * @param date        doafter this date
     */
    public DoAfterTasks(String description, String date) {
        super(description);
        super.setDate(date);
        super.symbol = "DAT";

    }
}
