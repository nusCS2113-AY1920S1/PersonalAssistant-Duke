package spinbox.entities.items.tasks;

import spinbox.DateTime;

public class Lecture extends Event {
    public Lecture(String description, DateTime startDate, DateTime endDate) {
        super(description, startDate, endDate);
        taskType = TaskType.LECTURE;
    }

    public Lecture(int done, String description, DateTime startDate, DateTime endDate) {
        super(done, description, startDate, endDate);
        taskType = TaskType.LECTURE;
    }
}
