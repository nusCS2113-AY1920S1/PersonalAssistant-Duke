package duke.command;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific {@link Command} used to delete a {@link Task} from the {@link TaskList}.
 */
public class DeleteCommand extends Command {
    private int taskNb;

    public DeleteCommand(int taskNb) {
        this.taskNb = taskNb;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        if (taskNb < taskList.size() && taskNb >= 0) {
            Task removed = taskList.removeTask(taskNb);
            List<String> fileContent = null;
            try {
                fileContent = new ArrayList<>(Files.readAllLines(storage.getPath(), StandardCharsets.UTF_8));
                fileContent.remove(taskNb); // changing the file content
                Files.write(storage.getPath(), fileContent, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new DukeException("Error while deleting the task from the had disc");
            }
            ui.showRemovedTask(removed.toString(), taskList.size());
        } else {
            throw new DukeException("Enter a valid task number after delete, between 1 and " + taskList.size());
        }
    }
}
