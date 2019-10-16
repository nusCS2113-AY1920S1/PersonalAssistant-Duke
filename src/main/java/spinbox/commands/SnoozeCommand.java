package spinbox.commands;

import spinbox.DateTime;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SnoozeCommand extends Command {
    private int index;
    private int dateSize;
    private Date startDate;
    private Date endDate;

    /**
     * Constructor for rescheduling of Task objects, does some input checking.
     * @param index index of the task
     * @param components Components of the full command
     * @param fullCommand the full command
     * @throws InputException can throw error if there is invalid input
     */
    public SnoozeCommand(int index, String[] components, String fullCommand) throws InputException {
        String dateString;

        try {
            dateString = fullCommand.split("snooze\\s\\d+\\s")[1];
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("☹ OOPS!!! Please enter dates.");
        }

        this.index = index;
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List dates = parser.parse(dateString).get(0).getDates();
        this.dateSize = dates.size();

        if (this.dateSize == 1) {
            startDate = (Date) dates.get(0);
        } else if (this.dateSize == 2) {
            startDate = (Date) dates.get(0);
            endDate = (Date) dates.get(1);
        }
    }

    /**
     * Reschedule the dates of the task.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws SpinBoxException invalid input or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        try {
            List<Task> tasks = taskList.getList();
            Task indexedTask = tasks.get(index);
            Boolean isEvent = indexedTask instanceof Event;
            Boolean isDeadline = indexedTask instanceof Deadline;
            Boolean wrongDateSize = !((isEvent && dateSize == 2) || (isDeadline && dateSize == 1));
            if (!(isEvent || isDeadline)) {
                throw new InputException("☹ OOPS!!! You can only reschedule Event or Deadline.");
            }
            if (wrongDateSize) {
                throw new InputException("☹ OOPS!!! Please supply correct amount of dates.");
            }
            if (isEvent) {
                Event snoozeEvent = (Event) indexedTask;
                snoozeEvent.setStartDate(new DateTime(startDate));
                snoozeEvent.setEndDate(new DateTime(endDate));
            }
            if (isDeadline) {
                Deadline snoozeDeadline = (Deadline) indexedTask;
                snoozeDeadline.setStartDate(new DateTime(startDate));
            }
            List<String> formattedOutput = new ArrayList<>();
            formattedOutput.add("Noted. I've rescheduled this task:\n" + indexedTask.toString());
            return ui.showFormatted(formattedOutput);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        }
    }
}
