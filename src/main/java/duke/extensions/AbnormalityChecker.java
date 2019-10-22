package duke.extensions;

import duke.task.Event;
import duke.task.Task;
import duke.tasklist.TaskList;

public class AbnormalityChecker {
    private TaskList taskList;

    public AbnormalityChecker(TaskList taskList) {
        this.taskList = taskList;
    }
    public boolean checkEventClash(Event event) {
        Task task;
        for (int i=0; i<taskList.size(); i++) {
            task = taskList.get(i);
            if (task instanceof Event) {
                if (((Event) task).getDateTime().equals(event.getDateTime())) {
                    return true;
                }
            }
        }
        return false;
    }
}
