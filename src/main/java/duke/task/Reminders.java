package duke.task;

//@@author gervaiseang

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a reminder class.
 */
public class Reminders implements Serializable {
    /**
     * Retrieves rem checks for tasks that are due soon.
     * @param remindDaysFrom gets the remind by that date time.
     * @param items The task list that contains a list of tasks.
     * @return TaskList.
     */
    public TaskList getReminders(int remindDaysFrom, TaskList items) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime remindByDateTime = currentDateTime.plusDays(remindDaysFrom);
        TaskList myTaskList = new TaskList();
        items.getTasks().forEach(task -> {
            if (task.getDateTime() != null) {
                String str = task.getDateTime();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                LocalDateTime taskDue = LocalDateTime.parse(str, formatter);
                if (taskDue.isAfter(currentDateTime) && taskDue.isBefore(remindByDateTime)) {
                    myTaskList.add(task);
                }
            }
        });
        return myTaskList;
    }
}
