package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.text.DecimalFormat;

public class CommandUpdateBalance extends Command {

    private Double newBalance;

    /**
     * Constructor for the CommandUpdateBalance class.
     * @param userInput The user Input from the CLI
     */
    public CommandUpdateBalance(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.SETBALANCE;
        this.description = "Updates current balance to new balance in the wallet \n"
                + "FORMAT :  ";
    }

    @Override
    public void execute(StorageManager storageManager) {
        try {
            this.newBalance = extractAmount();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        storageManager.setWalletBalance(this.newBalance);
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr("Balance updated to: $" + decimalFormat.format(this.newBalance) + "\n");
    }

    private Double extractAmount() throws DukeException {
        String incomeStr = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        incomeStr = incomeStr.trim().replace("$", "");
        try {
            return Double.parseDouble(incomeStr);
        } catch (Exception e) {
            throw new DukeException("Invalid amount entered.\n");
        }
    }
}
