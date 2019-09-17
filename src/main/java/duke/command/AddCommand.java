package duke.command;

import java.io.IOException;
import java.time.LocalDateTime;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;

/**
 * Class representing a command to add a new task.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Creates a new AddCommand with the given task.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Checks if entered event date is unique.
     *
     * @param tasks
     * @return true if event date is unique
     * @throws DukeException if event date is not unique.
     */
    public boolean checkEventDateIsUnique(TaskList tasks) throws DukeException {
        if(task instanceof Event) {
            LocalDateTime currentDate = ((Event) task).getDate();
            for(int i = 1; i <= tasks.size(); i++) {
                if(tasks.get(i) instanceof Event) {
                    LocalDateTime taskListDate = ((Event) tasks.get(i)).getDate();
                    if(currentDate.isEqual(taskListDate)) {
                        throw new DukeException("Event scheduling conflict!");
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if task is duplicate and already exist in TaskList
     *
     * @param tasks
     * @return true if task is not a duplicate
     * @throws DukeException if task is duplicate and already exist in TaskList
     */
    public boolean checkDuplicateTask(TaskList tasks) throws DukeException {
        String currentDesc = task.getDescription();
        if(task instanceof Todo) {
            for(int i = 1; i <= tasks.size(); i++) {
                if(tasks.get(i) instanceof Todo) {
                    String tasksDesc = tasks.get(i).getDescription();
                    if(tasksDesc.equals(currentDesc)) {
                        throw new DukeException("Todo task conflict!");
                    }
                }
            }

        } else if(task instanceof Deadline) {
            LocalDateTime currentTaskDate = ((Deadline) task).getDate();
            if(task instanceof Deadline) {
                for(int i = 1; i <= tasks.size(); i++) {
                    if(tasks.get(i) instanceof Deadline) {
                        String tasksDesc = tasks.get(i).getDescription();
                        LocalDateTime tasksDate = ((Deadline) tasks.get(i)).getDate();
                        if(tasksDesc.equals(currentDesc) && tasksDate.isEqual(currentTaskDate)) {
                            throw new DukeException("Deadline task conflict!");
                        }
                    }
                }
            }

        } else if(task instanceof Event) {
            LocalDateTime currentTaskDate = ((Event) task).getDate();
            if(task instanceof Event) {
                for(int i = 1; i <= tasks.size(); i++) {
                    if(tasks.get(i) instanceof Event) {
                        String tasksDesc = tasks.get(i).getDescription();
                        LocalDateTime tasksDate = ((Event) tasks.get(i)).getDate();
                        if(tasksDesc.equals(currentDesc) && tasksDate.isEqual(currentTaskDate)) {
                            throw new DukeException("Event task conflict!");
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        checkDuplicateTask(tasks);
        checkEventDateIsUnique(tasks);
        tasks.add(task);
        ui.printMessage("Got it. I've added this task:");
        ui.printMessage(task.toString());
        int size = tasks.size();
        ui.printMessage("You now have " + size + " tasks in the list");

        try {
            storage.writeFile(tasks.export());
        } catch (IOException e) {
            ui.printError("Error writing tasks to file");
        }
    }
}
