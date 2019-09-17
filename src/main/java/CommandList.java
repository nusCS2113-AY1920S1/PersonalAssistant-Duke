public class CommandList extends Command{
    @Override
    public void execute(Ui ui, Tasklist tasks, Storage storage){
        ui.showAction(tasks.toString());
    }
}
