package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.UiCode;

import java.time.LocalDate;

public class CommandGetSpendingByDay extends Command {
    protected String userInput;
    protected Double totalMoney = 0.0;
    protected String userDateInput = null;
    public boolean isFutureDate = false;

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
        if (storageManager.getWallet().getReceipts().size() == 0) {
            outputError("No receipts found in storage");
            return;
        }
        try {
            userDateInput = Parser.parseForPrimaryInput(CommandType.EXPENDEDDAY, userInput);

            checkIfDateIsInFuture(userDateInput);
            if (isFutureDate) {
                totalMoney = storageManager.getReceiptsByDate(userDateInput).getNettCashSpent();
                outputMessageOnGui("The total amount of money spent on "
                        + userDateInput + " is $" + totalMoney
                        + "\nNOTE : The date input is in the future");
//                outputError("Date input is in the future");
                return;
            }
            if (userDateInput.equals("today")) {
                String dateToday = LocalDate.now().toString();
                totalMoney = storageManager.getReceiptsByDate(dateToday).getNettCashSpent();
                outputMessageOnGui("The total amount of money spent today "
                        + "(" + dateToday + ") " + "is $" + totalMoney);
                return;
            } else if (userDateInput.equals("yesterday")) {
                String dateYesterday = LocalDate.now().minusDays(1).toString();
                totalMoney = storageManager.getReceiptsByDate(dateYesterday).getNettCashSpent();
                outputMessageOnGui("The total amount of money spent yesterday "
                        + "(" + dateYesterday + ") " + "is $" + totalMoney);
                return;
            } else {
                totalMoney = storageManager.getReceiptsByDate(userDateInput).getNettCashSpent();
                outputMessageOnGui("The total amount of money spent on "
                        + userDateInput + " is $" + totalMoney);
            }
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
    }

    /**
     * Function to output a String message as a pop-up below GUI when an error is encountered.
     * @param data is the output message
     */
    public void outputError(String data) {
        this.infoCapsule.setCodeError();
        this.infoCapsule.setOutputStr(data);
    }

    /**
     * Function to output a String message on the GUI when an error is encountered.
     * @param errorMessage is the output message
     */
    public void outputMessageOnGui(String errorMessage) {
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(errorMessage);
    }

    public void checkIfDateIsInFuture(String userDateInput) {
        LocalDate tempDate = LocalDate.parse(userDateInput);
        if(tempDate.isAfter(LocalDate.now())){
            isFutureDate = true;
        }
    }
}
