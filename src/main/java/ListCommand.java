public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks,Ui ui,FileHandling storage) throws DukeException {
        ui.printList(tasks.getAllTasks());
    }
}