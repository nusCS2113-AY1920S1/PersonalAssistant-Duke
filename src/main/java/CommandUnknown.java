public class CommandUnknown extends Command {
    @Override
    public void execute(Ui ui, Tasklist tasks, Storage storage){
        ui.showAction("     Unrecognized command\n");
    }
}
