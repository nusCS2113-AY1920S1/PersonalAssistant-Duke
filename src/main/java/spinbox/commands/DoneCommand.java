package spinbox.commands;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.tasks.Task;
import spinbox.Ui;

import java.util.ArrayList;
import java.util.List;

public class DoneCommand extends Command {
    private int index;

    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Mark task in task list as done.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws SpinBoxException invalid index or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        try {
            List<String> formattedOutput = new ArrayList<>();
            Task completed = taskList.mark(index);
            taskList.remove(index);
            formattedOutput.add("Nice! I've marked this task as done:");
            formattedOutput.add(completed.toString());
            formattedOutput.add("This task has been removed from the list.");
            taskList.saveData();
            List<Task> tasks = taskList.getList();
            formattedOutput.add("You currently have " + tasks.size()
                    + ((tasks.size() == 1) ? " task in the list." : " tasks in the list."));
            return ui.showFormatted(formattedOutput);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        }
    }
}
