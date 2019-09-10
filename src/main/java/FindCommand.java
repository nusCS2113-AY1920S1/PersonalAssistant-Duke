public class FindCommand extends Command {
    private String taskName;

    public FindCommand(String name) {
        super();
        this.taskName = name;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        ui.taskFound(tasks.fullTaskList(), taskName);
    }
}