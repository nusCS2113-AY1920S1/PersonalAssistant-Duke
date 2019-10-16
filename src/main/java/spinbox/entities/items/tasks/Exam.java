package spinbox.entities.items.tasks;

import spinbox.DateTime;

public class Exam extends Event {
    public Exam(String description, DateTime startDate, DateTime endDate) {
        super(description, startDate, endDate);
        taskType = TaskType.EXAM;
    }

    public Exam(int done, String description, DateTime startDate, DateTime endDate) {
        super(done, description, startDate, endDate);
        taskType = TaskType.EXAM;
    }
}
