public class CommandAdd extends Command {
    private Task task;
    public CommandAdd(Task task){
        this.task = task;
    }

    public void execute(Ui ui, Tasklist tasks, Storage storage){
        ui.showAction(tasks.add(task));
    }
}
