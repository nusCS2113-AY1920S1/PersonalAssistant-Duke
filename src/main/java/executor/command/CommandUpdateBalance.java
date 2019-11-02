package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

import java.text.DecimalFormat;

public class CommandUpdateBalance extends Command {

    private Double newBalance;

    /**
     * Constructor for the CommandUpdateBalance class.
     * @param userInput The user Input from the CLI
     */
    public CommandUpdateBalance(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.SETBALANCE;
        this.newBalance = extractAmount();
        this.description = "Updates current balance to new balance in the wallet \n"
                + "FORMAT :  \n";
    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        wallet.setBalance(this.newBalance);
        Ui.dukeSays("Balance updated to: $" + decimalFormat.format(this.newBalance));
        Ui.printSeparator();
    }

    private Double extractAmount() {
        String incomeStr = Parser.parseForPrimaryInput(this.commandType, this.userInput);
        incomeStr = incomeStr.trim().replace("$", "");
        try {
            return Double.parseDouble(incomeStr);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
