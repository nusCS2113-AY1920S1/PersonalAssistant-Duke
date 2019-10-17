package duke.command;

import duke.exceptions.DukeException;
import duke.storage.FileHandling;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.List;

public class DeleteCommand extends Command {
    private List<String> splitInput;

    public DeleteCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage) throws DukeException {
        try {
            int temp = Integer.parseInt(splitInput.get(1)) - 1;
            String taskA = tasks.getTask(temp).toString();
            tasks.deleteTask(temp);
            ui.deleteMessage(tasks.getAllTasks(), taskA);
            storage.saveData(tasks);
        } catch (NumberFormatException obj) {
            throw new DukeException(" OOPS! Enter a positive integer after \"delete\"");
        } catch (IndexOutOfBoundsException obj) {
            throw new DukeException(" OOPS! Enter a number that is present in the list");
        }
    }
}