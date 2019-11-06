package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;
import ui.IncomeReceipt;

public class CommandAddIncomeReceipt extends CommandAddReceipt {

    /**
     * Constructor for CommandAddIncomeReceipt subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandAddIncomeReceipt(String userInput) {
        super();
        this.commandType = CommandType.IN;
        this.userInput = userInput;
        this.cash = extractIncome(this.commandType, this.userInput);

        if (this.cash == 0) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Cash value cannot be $0.");
            return;
        }

        this.date = extractDate(this.userInput);
        this.tags = extractTags(this.userInput);
        this.description = "You can add a new income receipt. \n"
                + "FORMAT : in <value> /date <YYYY-MM-DD> /tags <tag>";
    }

    @Override
    public void execute(StorageManager storageManager) {

        IncomeReceipt r = new IncomeReceipt(this.cash, this.date, this.tags);
        try {
            storageManager.addReceipt(r);
            this.infoCapsule.setCodeToast();
            this.infoCapsule.setOutputStr("Added Income Receipt: $"
                    + r.getCashGained().toString()
                    + " "
                    + "with tags: "
                    + r.getTags().toString());
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.toString());
        }
    }
}
