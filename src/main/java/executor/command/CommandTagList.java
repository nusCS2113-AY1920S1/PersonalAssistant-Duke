package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.ReceiptTracker;
import java.text.DecimalFormat;

public class CommandTagList extends CommandList {
    private String tag;

    /**
     * Constructor for CommandTagList subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandTagList(String userInput) {
        super(userInput);
        this.userInput = userInput;
        this.description = "Lists based on tag \n"
                + "FORMAT : taglist <tag>";
        this.commandType = CommandType.TAGLIST;
        this.tag = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(StorageManager storageManager) {
        StringBuilder outputStr = new StringBuilder();
        if (this.tag == null || this.tag.isEmpty()) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Tag input is missing. FORMAT : taglist <tag>");
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        try {
            outputStr.append("You spent a total of $")
                .append(decimalFormat.format(getReceipt(this.tag, storageManager).getTotalExpenses()))
                .append(" ")
                .append("on")
                .append(" ")
                .append(this.tag)
                .append("\n")
                .append("\n")
                .append(getReceipt(this.tag, storageManager).getPrintableReceipts())
                .append("\n");
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr.toString());
    }

    /**
     * Returns receipts containing user input date.
     * @param tag String is date input from the user
     * @param storageManager Storagemanager the storage layer that holds the user data
     * @return ReceiptTracker that contains all the receipts
     * @throws DukeException no receipts containing input tag exist
     */
    private ReceiptTracker getReceipt(String tag, StorageManager storageManager) throws DukeException {
        ReceiptTracker tagList = storageManager.getReceiptsByTag(tag);
        if (tagList.isEmpty()) {
            throw new DukeException("No such tag found in the list");
        }
        return tagList;
    }
}


