package spinbox.commands;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.lists.TaskList;
import spinbox.items.tasks.Task;
import spinbox.Ui;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Deletes the relevant task in the task list.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws SpinBoxException Invalid index or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        try {
            List<String> formattedOutput = new ArrayList<>();
            List<Task> tasks = taskList.getList();

            Task removed = taskList.remove(index);
            taskList.saveData();
            formattedOutput.add("Noted. I've removed this task:\n" + removed.toString());
            formattedOutput.add("You currently have " + tasks.size()
                    + ((tasks.size() == 1) ? " task in the list." : " tasks in the list."));
            taskList.sort();

            return ui.showFormatted(formattedOutput);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        }
    }
}
