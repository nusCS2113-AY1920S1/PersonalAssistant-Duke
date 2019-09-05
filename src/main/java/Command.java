public abstract class Command {
    boolean isExit = false;

    public boolean isExit() {
        return this.isExit;
    }

    public abstract void execute(TaskList tasks,Ui ui,FileHandling storage) throws DukeException;
}