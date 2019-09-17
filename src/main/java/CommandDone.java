public class CommandDone extends Command {
    private int index;

    public CommandDone(int index){
        this.index = index;
    }

    @Override
    public void execute(Ui ui, Tasklist tasks, Storage storage){
        try {
            ui.showAction(tasks.done(index));
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("    The task index doesn't exist");
        }
    }
}
