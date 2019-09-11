import java.util.ArrayList;

public class AddCommand extends Command {
    private String content;

    public AddCommand(String content) {
        this.content = content;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        try {
            tasks.add(Parser.addCommand(content));
            storage.store(tasks);
            Ui.print("Got it. I've added this task: \n" + tasks.get(tasks.size() - 1)
                    + "\nNow you have " + tasks.size() + " tasks in the list.");
        } catch (DukeException e) {
            throw e;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
