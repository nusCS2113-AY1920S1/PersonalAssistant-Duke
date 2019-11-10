package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class CommandDateList extends Command {
    private String date;

    /**
     * Constructor for CommandDateList subCommand Class.
     *
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
        String outputStr = "You have the following receipts for" + " " + date + "\n\n";
        if (!isDateFormat(this.date)) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid date input. FORMAT : datelist yyyy-mm-dd");
            return;
        }
        try {
            outputStr += storageManager.getReceiptsByDate(this.date).getPrintableReceipts();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid input. FORMAT : datelist yyyy-mm-dd");
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }

    /**
     * Boolean function for checking date format of input.
     *
     * @param dateString String is date input from the CLI
     */
    private boolean isDateFormat(String dateString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            return format.parse(dateString) != null;
        } catch (ParseException e) {
            return false;
        }
    }

}

