package spinbox.entities.items.tasks;

import spinbox.DateTime;

public class Tutorial extends Event {

    public Tutorial(String description, DateTime startDate, DateTime endDate) {
        super(description, startDate, endDate);
        taskType = TaskType.TUTORIAL;
    }

    public Tutorial(int done, String description, DateTime startDate, DateTime endDate) {
        super(done, description, startDate, endDate);
        taskType = TaskType.TUTORIAL;
    }
}
