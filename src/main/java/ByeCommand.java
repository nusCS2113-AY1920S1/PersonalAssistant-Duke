public class ByeCommand extends Command {

    @Override
    public void execute(TaskList tasks,Ui ui,FileHandling storage) throws DukeException {
        isExit = true;
        ui.exitDuke();
    }
}