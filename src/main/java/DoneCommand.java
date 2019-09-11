import java.text.ParseException;
import java.util.ArrayList;

public class DoneCommand extends Command {
    private String line;

    public DoneCommand(String line) {
        this.line = line;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        try {
            int order = Integer.parseInt(line);
            tasks.get(order - 1).markAsDone();
            storage.store(tasks);
            Ui.print("Nice! I've marked this task as done: \n" + tasks.get(order - 1));
        } catch (Exception e) {
            throw new DukeException("Not a valid task number");
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
