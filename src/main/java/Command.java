abstract public class Command {
    boolean isExit = false;

    public boolean isExit() {
        return this.isExit;
    }

    abstract public void execute(TaskList tasks,Ui ui,FileHandling storage) throws DukeException;
}