import java.util.ArrayList;

public class FindCommand extends Command {
    private String line;

    public FindCommand(String line) {
        this.line = line.trim();
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) throws DukeException {
        String output = "Here are the matching tasks in your list:";
        int resultCount = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).description.contains(line)) {
                output += "\n" + (resultCount++) + "." + tasks.get(i);
            }
        }
        Ui.print(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
