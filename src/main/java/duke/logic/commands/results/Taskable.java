package duke.logic.commands.results;

import duke.model.lists.TaskList;

public interface Taskable {

    TaskList getTasks();

    void setTasks(TaskList tasks);
}
