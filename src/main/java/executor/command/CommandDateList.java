package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import java.time.format.DateTimeParseException;

public class CommandDateList extends Command {
    private String date;

    /**
     * Constructor for CommandDateList subCommand Class.
     * @param userInput String is the user input from the CLI
     */
    public CommandDateList(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Lists based on date. \n"
                + "Format: datelist <date>";
        this.commandType = CommandType.DATELIST;
        this.date = Parser.parseForPrimaryInput(this.commandType, userInput);
    }

    @Override
    public void execute(StorageManager storageManager) {
        if (this.date == null || this.date.isEmpty()) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Date input is missing. FORMAT : datelist yyyy-mm-dd");
            return;
        }
        String outputStr;
        try {
            outputStr = "You have the following receipts for" + " " + date;
            outputStr += storageManager.getReceiptsByDate(this.date).getPrintableReceipts();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input. FORMAT : datelist yyyy-mm-dd");
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }
}

