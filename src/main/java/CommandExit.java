public class CommandExit extends Command {
    @Override
    public void execute(Ui ui, Tasklist tasks, Storage storage){
        ui.showGoodbye();;
    }
}
