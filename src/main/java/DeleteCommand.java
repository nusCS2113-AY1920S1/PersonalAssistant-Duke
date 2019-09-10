public class DeleteCommand extends Command {
    private int Id;

    public DeleteCommand(int taskId) {
        super();
        this.Id = taskId;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        try {
            Task t = tasks.getTask(Id);
            tasks.deleteTask(Id);
            ui.taskRemoved(t, tasks.getSize());
            storage.save(tasks.fullTaskList());
        }
        catch (IndexOutOfBoundsException e)
        {
            ui.showError(e.getMessage());
        }
    }
}
