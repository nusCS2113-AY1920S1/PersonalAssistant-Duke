package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.text.DecimalFormat;

public class CommandPercent extends Command {
    private String tag;

    /**
     * Constructor for CommandPercent subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandPercent(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Gives the percentage of wallet expenses spent on a tag \n"
                + "FORMAT : percent <tag>";
        this.commandType = CommandType.PERCENT;
        this.tag = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(StorageManager storageManager) {
        StringBuilder outputStr = new StringBuilder();
        if (this.tag == null || this.tag.isEmpty()) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Tag input is missing. FORMAT : percent <tag>");
            return;
        }
        if (storageManager.getWallet().getReceipts().getPrintableReceipts().isEmpty()) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("No receipts found in the list");
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        try {
            Double totalTag = storageManager.getReceiptsByTag(this.tag).getTotalExpenses();
            Double totalSpent = storageManager.getWalletExpenses();
            outputStr.append(decimalFormat.format(((totalTag / totalSpent) * 100)))
                     .append("%")
                     .append(" ")
                     .append("of your wallet expenses is spent on")
                     .append(" ")
                     .append(this.tag)
                     .append("\n");
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr.toString());
    }
}
