package duke.commands;

import duke.DateTime;
import duke.exceptions.InputException;
import duke.Storage;
import duke.TaskList;
import duke.items.tasks.Task;
import duke.Ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewScheduleCommand extends Command {
    private Date inputDate;

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
            com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
            List dates;
            dates = parser.parse(fullCommand.substring(fullCommand.indexOf(" ") + 1)).get(0).getDates();
            inputDate = (Date) dates.get(0);
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
        List<Task> tasks = taskList.getTasks();

        for (Task task : tasks) {
            if (task.compareEquals(new DateTime(inputDate))) {
                formattedOutput.add((resultCount++ + 1) + ". " + task.toString());
            }
        }

        return ui.showFormatted(formattedOutput);
    }
}
