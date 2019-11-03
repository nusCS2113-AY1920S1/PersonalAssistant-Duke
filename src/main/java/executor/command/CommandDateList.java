package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;



public class CommandDateList extends Command {
    private String date;

    //Constructor
    /**
     * Constructor for CommandListMonYear subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandDateList(String userInput) {
        this.userInput = userInput;
        this.description = "Lists receipts based on date input. Format: datelist <date>";
        this.commandType = CommandType.DATELIST;
        this.date = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        ReceiptTracker dateReceipts = wallet.getReceipts().findReceiptsByDate(this.date);
        Ui.dukeSays("You have the following receipts for" + " " + date);
        Ui.printSeparator();
        dateReceipts.printReceipts();
        Ui.printSeparator();
    }

}

