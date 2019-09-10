public class DoneCommand extends Command {
    private int Id;

    public DoneCommand(int taskId) {
        super();
        this.Id = taskId;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        try
        {
            Task t = tasks.getTask(Id);
            t.markAsDone();
            storage.save(tasks.fullTaskList());
            ui.markedAsDone(t);
        }
        catch (IndexOutOfBoundsException e)
        {
            ui.showError(e.getMessage());
        }
    }
}
