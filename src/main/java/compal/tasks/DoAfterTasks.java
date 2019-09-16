package compal.tasks;

import java.util.Date;

public class DoAfterTasks extends Task {

    public DoAfterTasks(String description, Date date) {
        super(description);
        super.setDateTime(date);
        super.symbol="DAT";

    }
}
