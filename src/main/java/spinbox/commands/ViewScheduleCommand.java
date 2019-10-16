package spinbox.commands;

import spinbox.DateTime;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.tasks.Task;
import spinbox.Ui;

import java.util.ArrayList;
import java.util.List;

public class ViewScheduleCommand extends Command {
    private DateTime inputDate;

    /**
     * Constructor for viewing schedule for a day.
     * @param components Components of the full command.
     * @param fullCommand the full command.
     * @throws InputException if input format is incorrect.
     */
    public ViewScheduleCommand(String[] components, String fullCommand) throws InputException {
        if (components.length == 1) {
            throw new InputException("Please input one date");
        }

        try {
            inputDate = new DateTime(fullCommand.substring(fullCommand.indexOf(" ") + 1));
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Please ensure that you enter the full command.\n"
                    + "view-schedule <MM/DD/YYYY HH:MM>");
        }
    }

    /**
     * Searches for tasks on the same day as the given day.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        int resultCount = 0;
        ArrayList<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("Here are the tasks on the date given:");
        taskList.sort();
        List<Task> tasks = taskList.getList();

        for (Task task : tasks) {
            if (task.isSchedulable()) {
                if (((Schedulable) task).compareEquals(inputDate)) {
                    formattedOutput.add((resultCount++ + 1) + ". " + task.toString());
                }
            }
        }

        return ui.showFormatted(formattedOutput);
    }
}
