import java.util.ArrayList;

public class DeleteCommand extends Command {

    private String line;

    public DeleteCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        try {
            int order = Integer.parseInt(line);
            Ui.print("Noted. I've removed this task: \n" + tasks.remove(order - 1));
            storage.store(tasks);
        } catch (Exception e) {
            throw new DukeException("Not a valid task number");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
