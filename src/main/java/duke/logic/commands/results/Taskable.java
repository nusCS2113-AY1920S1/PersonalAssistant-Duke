package duke.logic.commands.results;

import duke.model.TaskList;

public interface Taskable {

    TaskList getTasks();

    void setTasks(TaskList tasks);
}
