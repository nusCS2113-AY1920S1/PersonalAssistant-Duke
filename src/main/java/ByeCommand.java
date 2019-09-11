import java.util.ArrayList;

public class ByeCommand extends Command {
    public ByeCommand() {

    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage) {
        Ui.print("Bye. Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
