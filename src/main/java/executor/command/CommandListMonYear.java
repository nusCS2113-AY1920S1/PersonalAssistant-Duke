package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;



public class CommandListMonYear extends Command {
    private String userInput;

    //Constructor
    /**
     * Constructor for CommandListMonYear subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandListMonYear(String userInput) {
        this.userInput = userInput;
        this.description = "Lists based on month and year.FORMAT: listmy <month> /year <year>";
        this.commandType = CommandType.LISTMY;

    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        ReceiptTracker listReceipts = new ReceiptTracker();
        int month = Integer.parseInt(Parser.parseForPrimaryInput(this.commandType, userInput));
        int year = Integer.parseInt(Parser.parseForFlag("year", userInput));
        listReceipts = wallet.getReceipts().findReceiptByMonthYear(month, year);
        Ui.dukeSays("You have the following receipts for" + userInput);
        Ui.printSeparator();
        listReceipts.printReceipts();
        Ui.printSeparator();
    }
}

