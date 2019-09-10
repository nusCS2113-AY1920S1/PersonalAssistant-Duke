import java.io.IOException;

/**
 * Command to mark task as done
 */
public class DoneCommand extends Command {
    private int n;
    public DoneCommand(String[] splitStr) throws DukeException {
        if (splitStr.length == 1)
            throw new DukeException("☹ OOPS!!! Please add the index of the task you have completed");
        n = Integer.parseInt(splitStr[1]);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        if (n < 1 || n > tasks.size()) throw new DukeException("☹ OOPS!!! That task is not in your list");
        tasks.get(n - 1).markAsDone();
        storage.saveToFile(tasks);
    }
}
