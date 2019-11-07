package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.Wallet;

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
        try {
            String stringTag = storageManager.getReceiptsByTag(this.tag).getTotalCashSpent().toString();
            Double totalTag = Double.parseDouble(stringTag);
            String stringSpent = storageManager.getWalletExpenses().toString();
            Double totalSpent = Double.parseDouble(stringSpent);
            outputStr.append((totalTag / totalSpent) * 100)
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
