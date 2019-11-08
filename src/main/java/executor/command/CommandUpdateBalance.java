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
        this.description = "Updates current balance to new balance in the wallet and can only be set once \n"
                + "FORMAT : setbalance $<amount>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        if (this.hasBeenSetAlready()) {
            this.infoCapsule.setCodeToast();
            this.infoCapsule.setOutputStr("Setbalance can be only set once !!\n");
        } else {
            try {
                this.newBalance = extractAmount();
            } catch (DukeException e) {
                this.infoCapsule.setCodeError();
                this.infoCapsule.setOutputStr(e.getMessage());
                return;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            storageManager.setWalletBalance(this.newBalance);
            this.infoCapsule.setCodeToast();
            this.infoCapsule.setOutputStr("Balance updated to: $" + decimalFormat.format(this.newBalance) + "\n");
            getExecutedCommands().add(this.commandType.toString());
        }
    }

    private Double extractAmount() throws DukeException {

        String incomeStr = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        try {
            incomeStr = incomeStr.trim().replace("$", "");
            Double amount = Double.parseDouble(incomeStr);
            if (amount < 0) {
                throw new Exception();
            }
            return amount;
        } catch (Exception e) {
            throw new DukeException("Please kindly follow the format : setbalance $<amount> \n"
            + "Please enter an amount greater than or equal to zero in your wallet !\n");
        }
    }

    private boolean hasBeenSetAlready() {
        return getExecutedCommands().contains(this.commandType.toString());
    }

}
