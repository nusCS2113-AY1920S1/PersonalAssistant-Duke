package duke.logic.commands.results;

import duke.model.lists.TaskList;

/**
 * Interface representing a command result containing a task list.
 */
public interface Taskable {

    TaskList getTasks();

    void setTasks(TaskList tasks);
}
