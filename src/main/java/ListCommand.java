import java.util.ArrayList;

public class ListCommand extends Command {
    public ListCommand() {

    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) {
        String output = "Here are the tasks in your list:";
        for (int i = 0;i < tasks.size();i++) {
            output += "\n" + (i + 1) + "." + tasks.get(i);
        }
        Ui.print(output);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
