package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.constants.TaskCommands;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import com.nwjbrandon.duke.services.ui.Terminal;

import java.text.ParseException;

public class FixedDuration extends Task {

    /**
     * Create fixed duration task from command line.
     * @param taskName task name.
     * @param numberOfTasks number of tasks.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    public FixedDuration(String taskName, int numberOfTasks) throws DukeWrongCommandFormatException {
        super(taskName, numberOfTasks);
    }

    /**
     * Create event task from file input.
     * @param taskDetails task information from file input.
     * @param numberOfTasks number of tasks.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    public FixedDuration(String[] taskDetails, int numberOfTasks) throws DukeWrongCommandFormatException {
        this(taskDetails[2] + " (needs " + taskDetails[3] + ")", numberOfTasks);
    }

    /**
     * Format name of task before creating task.
     * @param taskName task name.
     * @return formatted task name.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    @Override
    public String formatTaskName(String taskName) throws DukeWrongCommandFormatException {
        String[] parts = taskName.split("needs ");
        parts[0] = parts[0].substring(0, parts[0].length() - 2);
        parts[1] = parts[1].substring(0, parts[1].length() - 1);
        try {
            this.taskName = parts[0];
            this.by = parts[1];
            return parts[0] + " (needs: " + parts[1] + ")";
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeWrongCommandFormatException(TaskCommands.FIXED_DURATION.toString());
        }
    }

    /**
     * Add task.
     * @param size number of tasks.
     */
    @Override
    public void addTaskString(int size) {
        Terminal.showAddTaskString("[F][" + this.getStatusIcon() + "] " + this.getTaskDescription(), size);
    }

    /**
     * Remove task.
     * @param size number of tasks.
     */
    @Override
    public void removeTaskString(int size) {
        Terminal.showRemoveTaskString("[F][" + this.getStatusIcon() + "] " + this.getTaskDescription(), size);
    }

    /**
     * Get task description.
     * @return description of task.
     */
    @Override
    public String toTaskDescriptionString() {
        return "[F][" + this.getStatusIcon() + "] " + this.getTaskDescription();
    }

}
