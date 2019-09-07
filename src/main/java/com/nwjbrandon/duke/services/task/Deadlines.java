package com.nwjbrandon.duke.services.task;

import com.nwjbrandon.duke.constants.TaskCommands;
import com.nwjbrandon.duke.exceptions.DukeWrongCommandFormatException;
import com.nwjbrandon.duke.services.ui.Terminal;

import java.text.ParseException;

public class Deadlines extends Task {

    /**
     * Create deadline task from command input.
     * @param taskName task name.
     * @param numberOfTasks number of tasks.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    public Deadlines(String taskName, int numberOfTasks) throws DukeWrongCommandFormatException {
        super(taskName, numberOfTasks);
    }

    /**
     * Create deadline task from file input.
     * @param taskDetails task information from file input.
     * @param numberOfTasks number of tasks.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    public Deadlines(String[] taskDetails, int numberOfTasks) throws DukeWrongCommandFormatException {
        this(taskDetails[2] + " /by " + taskDetails[3], numberOfTasks);
    }

    /**
     * Format name of task before creating task.
     * @param taskName task name.
     * @return formatted task name.
     * @throws DukeWrongCommandFormatException command in wrong format.
     */
    @Override
    public String formatTaskName(String taskName) throws DukeWrongCommandFormatException {
        String[] parts = taskName.split(" /by ");
        try {
            this.taskName = parts[0];
            this.by = parts[1];
            return parts[0] + " (by: " + dateFormatter(parts[1]) + ")";
        } catch (ParseException e) {
            this.taskName = taskName;
            this.by = parts[1];
            return parts[0] + " (by: " + parts[1] + ")";
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeWrongCommandFormatException(TaskCommands.DEADLINE.toString());
        }
    }

    /**
     * Add task.
     * @param size number of tasks.
     */
    @Override
    public void addTaskString(int size) {
        Terminal.showAddTaskString("[D][" + this.getStatusIcon() + "] " + this.getTaskDescription(), size);
    }

    /**
     * Remove task.
     * @param size number of tasks.
     */
    @Override
    public void removeTaskString(int size) {
        Terminal.showRemoveTaskString("[D][" + this.getStatusIcon() + "] " + this.getTaskDescription(), size);
    }

    /**
     * Get task description.
     * @return description of task.
     */
    @Override
    public String toTaskDescriptionString() {
        return "[D][" + this.getStatusIcon() + "] " + this.getTaskDescription();
    }

}

