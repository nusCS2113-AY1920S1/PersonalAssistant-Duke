package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;
import java.time.LocalDate;

public class CommandGetSpendingByDay extends Command {
    protected String userInput;

    /**
     * Constructor to show what the class is able to do.
     * @param userInput is the input from the user
     */
    public CommandGetSpendingByDay(String userInput) {
        super();
        this.commandType = CommandType.EXPENDEDDAY;
        this.userInput = userInput;
        this.description = "Provides the user the total expenditure for the day stated. "
                + "FORMAT: expendedday <day>";
    }

    @Override
    public void execute(StorageManager storageManager) {
        String currDate = LocalDate.now().toString();
        Double totalMoney;
        try {
            totalMoney = storageManager.getReceiptsByDate(currDate).getTotalCashSpent();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr("The total amount of money spent today"
                + "(" + currDate + ") " + "is $" + totalMoney);
    }
}
