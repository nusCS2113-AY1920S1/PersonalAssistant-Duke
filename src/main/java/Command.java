public abstract class Command {

    abstract void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow;
    abstract boolean isExit();

}