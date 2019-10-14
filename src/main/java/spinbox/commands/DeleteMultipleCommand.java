package spinbox.commands;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.lists.TaskList;
import spinbox.items.tasks.Task;
import spinbox.Ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeleteMultipleCommand extends Command {
    private String indexes;
    private int inputSize;

    public DeleteMultipleCommand(String indexes, int inputSize) {
        this.indexes = indexes;
        this.inputSize = inputSize;
    }

    /**
     * Deletes multiple relevant tasks in the task list.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws SpinBoxException Invalid index or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        if (inputSize > 2) {
            throw new InputException("Ensure that the indexes are separated by ',' without any spacing. "
                    + "E.g. delete-multiple 2,3,4");
        }
        try {
            String[] splitIndexes = indexes.split(",");
            if (splitIndexes.length == 1) {
                throw new InputException("To delete a single task, provide the input in this format instead: "
                        + "delete <one index in integer form>");
            }
            List<String> formattedOutput = new ArrayList<>();
            List<Integer> finalIndexes = new ArrayList<>();
            for (int i = 0; i < splitIndexes.length; i++) {
                String convert = splitIndexes[i];
                finalIndexes.add(Integer.parseInt(convert) - 1);
            }
            Collections.sort(finalIndexes, Collections.reverseOrder());
            for (int i = 0; i < finalIndexes.size(); i++) {
                Task removed = taskList.remove(finalIndexes.get(i));
                if (i == 0) {
                    formattedOutput.add("Noted. I've removed these tasks:\n" + removed.toString());
                } else {
                    formattedOutput.add(removed.toString());
                }
            }
            List<Task> tasks = taskList.getList();
            storage.setData(tasks);
            formattedOutput.add("You currently have " + tasks.size()
                    + ((tasks.size() == 1) ? " task in the list." : " tasks in the list."));
            taskList.sort();
            return ui.showFormatted(formattedOutput);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        } catch (NumberFormatException e) {
            throw new InputException("Invalid index entered. Please ensure the index is in integer form. "
                    + "E.g. delete-multiple 2,5");
        }
    }
}