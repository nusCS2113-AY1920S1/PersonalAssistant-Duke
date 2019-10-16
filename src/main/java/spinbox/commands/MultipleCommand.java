package spinbox.commands;

import spinbox.exceptions.SpinBoxException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.entities.items.tasks.Task;
import spinbox.Ui;
import spinbox.containers.lists.TaskList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleCommand extends Command {
    private String commandType;
    private String indexes;

    /**
     * Constructor for multiple command.
     * @param commandType Either done-multiple or delete-multiple.
     * @param indexes Indexes of the tasks to be deleted.
     */
    public MultipleCommand(String commandType, String indexes) {
        this.commandType = commandType;
        this.indexes = indexes;
    }

    /**
     * Either delete multiple relevant tasks from the task list or
     * mark them as done and delete them.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws SpinBoxException Invalid index or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws SpinBoxException {
        int inputSize = indexes.split(" ").length;
        if (inputSize > 1) {
            throw new InputException("Ensure that the indexes are separated by ',' without any spacing. "
                    + "E.g. delete-multiple 2,3,4 or done-multiple 2,3,4");
        }
        try {
            String[] splitIndexes = indexes.split(",");
            if (splitIndexes.length == 1) {
                throw new InputException("To delete or mark a single task as done, provide the input in this "
                        + "format instead: delete <one index in integer form> or done <one index in integer form>.");
            }
            List<String> formattedOutput = new ArrayList<>();
            List<Integer> finalIndexes = new ArrayList<>();
            for (int i = 0; i < splitIndexes.length; i++) {
                String convert = splitIndexes[i];
                finalIndexes.add(Integer.parseInt(convert) - 1);
            }
            Collections.sort(finalIndexes, Collections.reverseOrder());
            if (commandType.equals("delete-multiple")) {
                for (int i = 0; i < finalIndexes.size(); i++) {
                    Task removed = taskList.remove(finalIndexes.get(i));
                    if (i == 0) {
                        formattedOutput.add("Noted. I've removed these tasks:\n" + removed.toString());
                    } else {
                        formattedOutput.add(removed.toString());
                    }
                }
            } else {
                for (int i = 0; i < finalIndexes.size(); i++) {
                    Task completed = taskList.mark(finalIndexes.get(i));
                    taskList.remove(finalIndexes.get(i));
                    if (i == 0) {
                        formattedOutput.add("Nice! I've marked these tasks as done:\n" + completed.toString());
                    } else {
                        formattedOutput.add(completed.toString());
                    }
                    if (i == finalIndexes.size() - 1) {
                        formattedOutput.add("The above tasks have also been removed from the list of tasks.");
                    }
                }
            }
            List<Task> tasks = taskList.getList();
            taskList.saveData();
            formattedOutput.add("You currently have " + tasks.size()
                    + ((tasks.size() == 1) ? " task in the list." : " tasks in the list."));
            return ui.showFormatted(formattedOutput);
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Invalid index entered. Type 'list' to see your list.");
        } catch (NumberFormatException e) {
            throw new InputException("Invalid index entered. Please ensure the index is in integer form. "
                    + "E.g. delete-multiple 2,5 or done-multiple 2,5");
        }
    }
}