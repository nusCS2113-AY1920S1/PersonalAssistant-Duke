package spinbox.commands;

import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.containers.lists.TaskList;
import spinbox.entities.items.tasks.Task;
import spinbox.Ui;

import java.util.ArrayList;
import java.util.List;

public class FindCommand extends Command {
    private String keyword;

    /**
     * Constructor for finding tasks using a keyword.
     * @param components Components of the full command
     * @param fullCommand the full command
     * @throws InputException missing keyword
     */
    public FindCommand(String[] components, String fullCommand) throws InputException {
        if (components.length == 1) {
            throw new InputException("☹ OOPS!!! You need to enter a keyword.");
        } else {
            keyword = fullCommand.replaceFirst("find ", "");
        }
    }

    /**
     * Searches for tasks using a keyword.
     * @param taskList TaskList instance
     * @param storage Storage instance
     * @param ui Ui instance
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        int resultCount = 0;
        ArrayList<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("Here are the matching tasks in your list:");
        List<Task> tasks = taskList.getList();

        for (Task task : tasks) {
            if (task.getName().contains(keyword)) {
                formattedOutput.add((resultCount++ + 1) + ". " + task.toString());
            }
        }
        return ui.showFormatted(formattedOutput);
    }
}
