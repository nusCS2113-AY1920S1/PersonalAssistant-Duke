import java.util.List;

public class DeleteCommand extends Command {
    List<String> words;

    public DeleteCommand(List<String> words) {
        this.words = words;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            int taskNo = Integer.parseInt(words.get(0)) - 1;
            Task toRemove = taskList.get(taskNo);
            taskList.delete(taskNo);
            ui.showDeletedTask(taskList.getTasks(), toRemove);
        } catch (NumberFormatException e) {
            throw new DukeException("Please supply a number. Eg: done 2");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please supply a valid number.");
        }

    }
}
