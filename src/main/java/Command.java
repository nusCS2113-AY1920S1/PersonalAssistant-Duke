import java.util.ArrayList;

public abstract class Command {
    public abstract void execute(ArrayList<Task> tasks, Storage storage) throws DukeException;

    public abstract boolean isExit();
}
