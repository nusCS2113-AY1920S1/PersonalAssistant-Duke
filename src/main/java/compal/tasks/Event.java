package compal.tasks;

import java.util.Date;

public class Event extends Task {

    public Event(String description, Date date) {
        super(description);
        super.symbol = "E";
        super.setDateTime(date);
    }


}
