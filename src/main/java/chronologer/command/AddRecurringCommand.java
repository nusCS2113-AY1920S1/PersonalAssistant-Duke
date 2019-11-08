package chronologer.command;

import java.time.LocalDateTime;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.ui.UiTemporary;

/**
 * Adds weekly recurring tasks to taskslist.
 * 
 * @author Hans kurnia
 * @version 1.0
 */
public class AddRecurringCommand extends AddCommand {

    /**
     * Contructs a new "add recurring command".
     * 
     * @param command         command-type of input
     * @param taskDescription description of task
     * @param startDate       start date of task
     * @param endDate         end date of task
     * @param modCode         module code of task
     */
    public AddRecurringCommand(String command, String taskDescription, LocalDateTime startDate, LocalDateTime endDate,
            String modCode) {
        super(command, taskDescription, startDate, endDate, modCode);
    }

    @Override
    /**
     * Adds tasks till end of semester at specified timeslots.
     */
    public void execute(TaskList tasks, Storage storage) throws ChronologerException {
        Task task;
        LocalDateTime timeNow = LocalDateTime.now();
        while (this.formattedStartDate.isAfter(timeNow)) {
            task = new Event(taskDescription, formattedStartDate, formattedEndDate, modCode);
            tasks.add(task);
            this.formattedEndDate = this.formattedEndDate.minusWeeks(1);
            this.formattedStartDate = this.formattedStartDate.minusWeeks(1);
        }
        ChronologerStateList.addState(tasks.getTasks());
        storage.saveFile(tasks.getTasks());
        UiTemporary.printOutput(
                "Got it! I've added this task" + "\nNow you have " + tasks.getSize() + " task(s) in the list.");
    }
}