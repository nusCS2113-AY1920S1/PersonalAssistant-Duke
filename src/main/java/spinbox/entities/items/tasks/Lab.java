package spinbox.entities.items.tasks;

import spinbox.DateTime;

public class Lab extends Event {
    public Lab(String description, DateTime startDate, DateTime endDate) {
        super(description, startDate, endDate);
        taskType = TaskType.LAB;
    }

    public Lab(int done, String description, DateTime startDate, DateTime endDate) {
        super(done, description, startDate, endDate);
        taskType = TaskType.LAB;
    }
}
