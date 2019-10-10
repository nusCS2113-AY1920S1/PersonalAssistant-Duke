package duke.commands;

import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import duke.Storage;
import duke.TaskList;
import duke.items.tasks.Task;
import duke.Ui;

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
     * @throws DukeException Invalid index or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        try {
            List<String> formattedOutput = new ArrayList<>();
            List<Task> tasks = taskList.getTasks();

            Task removed = taskList.removeTask(index);
            storage.setData(tasks);
            formattedOutput.add("Noted. I've removed this task:\n" + removed.toString());
            formattedOutput.add("You currently have " + tasks.size()
                    + ((tasks.size() == 1) ? " task in the list." : " tasks in the list."));
            return ui.showFormatted(formattedOutput);

        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        }
    }
}
