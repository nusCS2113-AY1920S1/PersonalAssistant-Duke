package spinbox.entities.items.tasks;

import java.util.EnumSet;

public enum TaskType {
    DEADLINE,
    TODO,
    EVENT,
    TUTORIAL,
    LAB,
    EXAM,
    LECTURE;

    public static EnumSet<TaskType> taskWithBothDates() {
        return EnumSet.of(EVENT, TUTORIAL, LAB, EXAM, LECTURE);
    }


}
