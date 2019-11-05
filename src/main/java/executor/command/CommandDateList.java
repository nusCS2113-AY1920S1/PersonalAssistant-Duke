package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

public class CommandDateList extends Command {
    private String date;

    /**
     * Constructor for CommandListMonYear subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandDateList(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Lists based on date. \n"
                + "Format: listmy <date>";
        this.commandType = CommandType.DATELIST;
        this.date = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr;
        try {
            outputStr = storageManager.getReceiptsByDate(this.date).getPrintableReceipts();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }
}

