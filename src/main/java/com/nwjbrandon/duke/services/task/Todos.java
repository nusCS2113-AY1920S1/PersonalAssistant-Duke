package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import com.nwjbrandon.duke.services.ui.Terminal;

public class Todos extends Task {

    /**
     * Create todos task from command line.
     * @param taskName task name.
     * @param numberOfTasks number of tasks.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    public Todos(String taskName, int numberOfTasks) throws DukeWrongCommandFormatException {
        super(taskName, numberOfTasks);
    }

    /**
     * Create event task from file input.
     * @param taskDetails task information from file input.
     * @param numberOfTasks number of tasks.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    public Todos(String[] taskDetails, int numberOfTasks) throws DukeWrongCommandFormatException {
        this(taskDetails[2], numberOfTasks);
    }

    /**
     * Format name of task before creating task.
     * @param taskName task name
     * @return formatted task name.
     */
    @Override
    public String formatTaskName(String taskName) {
        this.taskName = taskName;
        return taskName;
    }

    /**
     * Add task.
     * @param size number of tasks.
     */
    @Override
    public void addTaskString(int size) {
        Terminal.showAddTaskString("[T][" + this.getStatusIcon() + "] " + this.getTaskDescription(), size);
    }

    /**
     * Remove task.
     */
    @Override
    public void removeTaskString(int size) {
        Terminal.showRemoveTaskString("[T][" + this.getStatusIcon() + "] " + this.getTaskDescription(), size);
    }

    /**
     * Get task description.
     * @return description of task.
     */
    @Override
    public String toTaskDescriptionString() {
        return "[T][" + this.getStatusIcon() + "] " + this.getTaskDescription();
    }

}

