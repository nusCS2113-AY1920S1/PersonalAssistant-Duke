package owlmoney.logic.command;

import java.io.IOException;
import java.time.LocalDateTime;

import owlmoney.logic.exception.DukeException;
import owlmoney.storage.Storage;
import owlmoney.model.task.Deadline;
import owlmoney.model.task.Event;
import owlmoney.model.task.RecurringTask;
import owlmoney.model.task.Task;
import owlmoney.model.task.TaskList;
import owlmoney.model.task.Todo;
import owlmoney.ui.Ui;

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
     * Checks for duplicated events in the task list.
     *
     * @param currentDesc Description of event to check for duplication.
     * @param tasks The task list.
     * @param currentTaskDate Date of event to check for duplication.
     * @throws DukeException If there is a duplicate event.
     */
    public static void checkDuplicateEvent(String currentDesc,
            TaskList tasks, LocalDateTime currentTaskDate) throws DukeException {
        for (int i = 1; i <= tasks.size(); i++) {
            if (tasks.get(i) instanceof Event) {
                String tasksDesc = tasks.get(i).getDescription();
                LocalDateTime tasksDate = ((Event) tasks.get(i)).getDateTime();
                if (tasksDesc.equals(currentDesc) && tasksDate.isEqual(currentTaskDate)) {
                    throw new DukeException("Event task conflict!");
                }
            }
        }
    }

    /**
     * Checks for clash in event date.
     * @param tasks The task list.
     * @param task The task to check for clashes.
     * @throws DukeException If there is a clash of event timings.
     */
    public static void checkEventDateIsUnique(TaskList tasks, Task task) throws DukeException {
        if (task instanceof Event) {
            LocalDateTime currentDate = ((Event) task).getDateTime();
            for (int i = 1; i <= tasks.size(); i++) {
                if (tasks.get(i) != task) {
                    if (tasks.get(i) instanceof Event) {
                        LocalDateTime taskListDate = ((Event) tasks.get(i)).getDateTime();
                        if (currentDate.isEqual(taskListDate)) {
                            throw new DukeException("Event scheduling conflict!");
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks for duplicated recurring tasks.
     *
     * @param currentDesc The description of the recurring task to check for duplicates.
     * @param tasks The task list.
     * @param currentTaskDate The date of the recurring task to check for duplicates.
     * @throws DukeException If there is a duplicated recurring task.
     */
    public static void checkDuplicateRecurringTask(String currentDesc,
            TaskList tasks, LocalDateTime currentTaskDate) throws DukeException {
        for (int i = 1; i <= tasks.size(); i++) {
            if (tasks.get(i) instanceof RecurringTask) {
                String tasksDesc = tasks.get(i).getDescription();
                LocalDateTime tasksDate = ((RecurringTask) tasks.get(i)).getDateTime();
                if (tasksDesc.equals(currentDesc) && tasksDate.isEqual(currentTaskDate)) {
                    throw new DukeException("Recurring task conflict!");
                }
            }
        }
    }

    /**
     * Checks for duplicated deadline tasks.
     *
     * @param currentDesc The description of the deadline to check for duplicates.
     * @param tasks The task list.
     * @param currentTaskDate The date of the deadline to check for duplicates.
     * @throws DukeException If there is a duplicated deadline task.
     */
    public static void checkDuplicateDeadline(String currentDesc,
            TaskList tasks, LocalDateTime currentTaskDate) throws DukeException {
        for (int i = 1; i <= tasks.size(); i++) {
            if (tasks.get(i) instanceof Deadline) {
                String tasksDesc = tasks.get(i).getDescription();
                LocalDateTime tasksDate = ((Deadline) tasks.get(i)).getDateTime();
                if (tasksDesc.equals(currentDesc) && tasksDate.isEqual(currentTaskDate)) {
                    throw new DukeException("Deadline task conflict!");
                }
            }
        }
    }

    /**
     * Checks for duplicated todo tasks.
     *
     * @param currentDesc The description of the todo to check for duplicates.
     * @param tasks The task list.
     * @throws DukeException If there is a duplicated todo task.
     */
    public static void checkDuplicateTodo(String currentDesc, TaskList tasks) throws DukeException {
        for (int i = 1; i <= tasks.size(); i++) {
            if (tasks.get(i) instanceof Todo) {
                String tasksDesc = tasks.get(i).getDescription();
                if (tasksDesc.equals(currentDesc)) {
                    throw new DukeException("Todo task conflict!");
                }
            }
        }
    }

    /**
     * Checks if task is duplicate and already exist in TaskList.
     *
     * @param tasks The task list.
     * @throws DukeException If task is duplicate and already exist in TaskList.
     */
    public void checkDuplicateTask(TaskList tasks) throws DukeException {
        String currentDesc = task.getDescription();
        if (task instanceof Todo) {
            checkDuplicateTodo(currentDesc, tasks);
        } else if (task instanceof Deadline) {
            LocalDateTime currentTaskDate = ((Deadline) task).getDateTime();
            checkDuplicateDeadline(currentDesc, tasks, currentTaskDate);
        } else if (task instanceof Event) {
            LocalDateTime currentTaskDate = ((Event) task).getDateTime();
            checkDuplicateEvent(currentDesc, tasks, currentTaskDate);
        } else if (task instanceof RecurringTask) {
            LocalDateTime currentTaskDate = ((RecurringTask) task).getDateTime();
            checkDuplicateRecurringTask(currentDesc, tasks, currentTaskDate);
        }
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
        checkEventDateIsUnique(tasks, task);
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
