class ListCommand extends Command {
    public ListCommand() {
        super();
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) {
        ui.listTasks(tasks);
    }
}