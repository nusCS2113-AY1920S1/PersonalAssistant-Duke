package compal.tasks;

import java.util.Date;

public class Deadline extends Task {

    public Deadline(String description, Date date) {
        super(description);
        super.symbol = "D";
        super.setDateTime(date);
    }


}
