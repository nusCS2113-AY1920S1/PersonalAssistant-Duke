public class AddCommand extends Command {
    private Task t;

    public AddCommand(Task tt) {
        super();
        this.t = tt;
    }

    @Override
    public boolean isExit() {
        return false;
    }
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        tasks.addTask(t);
        ui.taskAdded(t, tasks.getSize());
        storage.save(tasks.fullTaskList());
    }
}
