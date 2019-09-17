public class CommandDelete extends Command{
    private int index;

    public CommandDelete(int index){
        this.index = index;
    }

    @Override
    public void execute(Ui ui, Tasklist tasks, Storage storage){
        try {
            ui.showAction(tasks.delete(index));
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("    The task index doesn't exist");
        }
    }
}
