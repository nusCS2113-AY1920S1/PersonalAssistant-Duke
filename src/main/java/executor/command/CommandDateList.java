package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.ReceiptTracker;
import ui.Ui;
import ui.Wallet;

import java.time.format.DateTimeParseException;


public class CommandDateList extends Command {
    private String date;

    //Constructor
    /**
     * Constructor for CommandListMonYear subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandDateList(String userInput) {
        this.userInput = userInput;
        this.description = "Lists based on date. \n"
                + "Format: datelist <date>";
        this.commandType = CommandType.DATELIST;
        this.date = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        try {
            if (date == null || date.isEmpty()) {
                Ui.dukeSays("Date input is missing. FORMAT : datelist yyyy-mm-dd");
                return;
            }
            ReceiptTracker dateReceipts = wallet.getReceipts().findReceiptsByDate(this.date);
            Ui.dukeSays("You have the following receipts for" + " " + date);
            Ui.printSeparator();
            dateReceipts.printReceipts();
            Ui.printSeparator();
        } catch (DateTimeParseException e) {
            Ui.dukeSays("Invalid input. FORMAT : datelist yyyy-mm-dd");
        }
    }

}

