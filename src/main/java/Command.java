import java.util.List;

abstract public class Command {
    protected boolean isExit;

    public boolean isExit() {
        return this.isExit;
    }

    abstract public void execute(TaskList taskList, Ui ui, Storage storage) throws DukeException;
}
