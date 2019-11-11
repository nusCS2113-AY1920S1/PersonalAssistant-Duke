package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;
import java.text.DecimalFormat;

public class CommandDisplayBalance extends Command {

    /**
     * Constructor for CommandDisplayBalance subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandDisplayBalance(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Shows the current balance available in the wallet \n"
                + "FORMAT: Balance";
        this.commandType = CommandType.BALANCE;
    }

    @Override
    public void execute(StorageManager storageManager) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String outputStr;
        try {
            outputStr = "Your Balance: $"
                    + decimalFormat.format(storageManager.getWalletBalance());
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr(outputStr);
    }
}
