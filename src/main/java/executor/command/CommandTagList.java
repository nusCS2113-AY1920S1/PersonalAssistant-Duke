package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import java.text.DecimalFormat;

public class CommandTagList extends CommandList {
    private String tag;

    /**
     * Constructor for CommandListTag subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandTagList(String userInput) {
        super(userInput);
        this.userInput = userInput;
        this.description = "Lists based on tag \n"
                + "FORMAT :  ";
        this.commandType = CommandType.TAGLIST;
        this.tag = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
       public void execute(StorageManager storageManager) {
        StringBuilder outputStr = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        try {
            outputStr.append("You spent a total of $")
                    .append(decimalFormat.format(storageManager.getReceiptsByTag(this.tag).getTotalCashSpent()))
                    .append(" ")
                    .append("on")
                    .append(" ")
                    .append(this.tag)
                    .append("\n")
                    .append(storageManager.getReceiptsByTag(this.tag).getPrintableReceipts())
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
