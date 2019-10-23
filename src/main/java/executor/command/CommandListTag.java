package executor.command;
import executor.task.TaskList;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;
public class CommandListTag extends CommandList {

    //Constructor
    /**
     * Constructor for CommandListTag subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandListTag(String userInput){
        super(userInput);
        this.userInput = userInput;
        this.description = "Lists based on tag";
        this.commandType = CommandType.TAGLIST;

    }
    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
       wallet.getReceipts().findReceiptsByTag(userInput); //this returns a receiptracker object
       ReceiptTracker a =  new ReceiptTracker();
       a.printReceipts();
    }
}
