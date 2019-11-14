package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;
import java.text.DecimalFormat;

public class CommandDisplayExpenditure extends Command {

    /**
     * Constructor for CommandDisplayExpenditure subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDisplayExpenditure(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Shows the total amount of money spent"
                + "FORMAT: Expenses";
        this.commandType = CommandType.EXPENSES;
    }

    @Override
    public void execute(StorageManager storageManager) {
        double totalExpenses;
        try {
            totalExpenses = storageManager.getWalletExpenses();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr("Total Expenditure: $"
                + decimalFormat.format(totalExpenses));
    }
}
