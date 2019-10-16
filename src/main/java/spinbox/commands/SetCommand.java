package spinbox.commands;

import spinbox.DateTime;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.Ui;
import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Task;

import java.util.ArrayList;

public class SetCommand extends Command {
    private int index;
    private DateTime startDate;
    private DateTime endDate;

    /**
     * Takes the input and extract index, startDate, and endDate.
     * @param components Components of the full command.
     * @param fullCommand the full command.
     * @throws InputException wrong format for set-tentative.
     */
    public SetCommand(String[] components, String fullCommand) throws InputException {
        try {
            this.index = Integer.parseInt(components[1]) - 1;

            startDate = new DateTime(fullCommand.split("/set ")[1], 0);
            endDate = new DateTime(fullCommand.split("/set ")[1], 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Please ensure that you enter the full command.\n"
                    + "set-tentative <index> /set <start as MM/DD/YYYY HH:MM>\n"
                    + "to <end as MM/DD/YYYY HH:MM>");
        } catch (NumberFormatException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        }
    }

    /**
     * Checks whether the task at index is tentative task. Then removes tentative
     * task and replace with event task.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @return Output for this command.
     * @throws SpinBoxException if task at index is not tentative task.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        Task tentativeTask;

        try {
            tentativeTask = taskList.getList().get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        }

        String firstComponent = tentativeTask.toString().split(" ")[0];
        String taskType = firstComponent.substring(0, firstComponent.lastIndexOf("["));
        if (!taskType.equals("[TE]")) {
            throw new InputException("The index given is not a tentative task.");
        }

        int tentativeTaskDone = tentativeTask.getDone() ? 1 : 0;
        Task addTask = new Event(tentativeTaskDone,
                (tentativeTask).getName(),
                this.startDate, this.endDate);
        taskList.replace(index, addTask);
        ArrayList<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("Replaced tentative event:");
        formattedOutput.add(tentativeTask.toString());
        formattedOutput.add("With event:");
        formattedOutput.add(addTask.toString());
        taskList.saveData();
        taskList.sort();

        return ui.showFormatted(formattedOutput);
    }
}
