package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;
import storage.wallet.Receipt;

import java.text.DecimalFormat;

public class CommandAddSpendingReceipt extends CommandAddReceipt {

    /**
     * Constructor for CommandAddSpendingReceipt subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandAddSpendingReceipt(String userInput) {
        super();
        this.commandType = CommandType.OUT;
        this.userInput = userInput;
        this.cash = extractIncome(this.commandType, this.userInput);

        if (this.cash == 0) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Cash value cannot be $0.");
            return;
        }

        this.date = extractDate(this.userInput);
        this.tags = extractTags(this.userInput);
        this.description = "You can add a new spendings receipt.\n"
                + "FORMAT :  out <value> /date <YYYY-MM-DD> /tags <tag>";
    }

    @Override
    public void execute(StorageManager storageManager) {

        Receipt r = new Receipt(this.cash, this.date, this.tags);
        try {
            storageManager.addReceipt(r);
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            this.infoCapsule.setCodeToast();
            this.infoCapsule.setOutputStr("Added Spending Receipt: $"
                    + decimalFormat.format(r.getCashSpent())
                    + " with tags: "
                    + r.getTags().toString());
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }

}
