package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import storage.wallet.ReceiptTracker;
import java.text.ParseException;
import java.text.SimpleDateFormat;


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
        String outputStr = "You have the following receipts for" + " " + date + "\n\n";
        if (!isDateFormat(this.date)) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Invalid date input. FORMAT : datelist yyyy-mm-dd");
            return;
        }
        try {
            outputStr += getReceipt(this.date, storageManager).getPrintableReceipts();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }

    /**
     * Boolean function for checking date format of input.
     * @param dateString String is date input from the CLI
     * @return retuns true if date is of the correct format false otherwise
     */
    private boolean isDateFormat(String dateString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            return format.parse(dateString) != null;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Returns receipts containing user input date.
     * @param date String is date input from the user
     * @param storageManager Storagemanager the storage layer that holds the user data
     * @return ReceiptTracker that contains all the receipts
     * @throws DukeException no receipts containing input date exist
     */
    private ReceiptTracker getReceipt(String date, StorageManager storageManager) throws DukeException {
        ReceiptTracker dateList = storageManager.getReceiptsByDate(date);
        if (dateList.isEmpty()) {
            throw new DukeException("You have no receipts for this date");
        }
        return dateList;
    }
}

