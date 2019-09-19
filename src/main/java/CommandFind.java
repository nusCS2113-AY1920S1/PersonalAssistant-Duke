public class CommandFind extends Command {
    private String keyword;

    public CommandFind(String keyword){
        this.keyword = keyword;
    }

    @Override
    public void execute(Ui ui, Tasklist tasks, Storage storage){
        ui.showAction(tasks.find(keyword));
    }
}
