package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;

import java.time.LocalDate;

public class CommandGetSpendingByDay extends Command {
    protected String userInput;
    private Double totalMoney = 0.0;
    private String userDateInput = null;
    private LocalDate dateInLocal;

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
        try {
            checkUserInput(storageManager);
            outputExpenditureForInput(storageManager);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }

    /**
     * Function to check the user input.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void checkUserInput(StorageManager storageManager) throws DukeException {
        userDateInput = Parser.parseForPrimaryInput(CommandType.EXPENDEDDAY, userInput);
        if (storageManager.getWallet().getReceipts().size() == 0) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("No receipts found in storage");
            return;
        }
        if (!userDateInput.equals("today") && (!userDateInput.equals("yesterday"))) {
            try {
                if (userDateInput.isEmpty()) {
                    throw new DukeException("No user input detected."
                            + format());
                }
                dateInLocal = LocalDate.parse(userDateInput);
            } catch (Exception e) {
                throw new DukeException("Input is invalid."
                        + format());
            }
        }
    }

    /**
     * Function to output String to display the correct format for expendedday command.
     * @return is the output String
     */
    private String format() {
        return " FORMAT : expendedday "
                + "\ntoday or"
                + "\nyesterday or"
                + "\nYYYY-MM-DD";
    }

    /**
     * Function to output the correct expenditure according to the user input.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void outputExpenditureForInput(StorageManager storageManager) throws DukeException {
        if (userDateInput.equals("today")) {
            expenditureForToday(storageManager);
        } else if (userDateInput.equals("yesterday")) {
            expenditureForYesterday(storageManager);
        } else if (dateInLocal.isAfter(LocalDate.now())) {
            expenditureForDateInFuture(storageManager);
        } else {
            expenditureForDate(storageManager);
        }
    }

    /**
     * Function that outputs the expenditure if the input date is in the future.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void expenditureForDateInFuture(StorageManager storageManager) throws DukeException {
        String tempDate = dateInLocal.toString();
        totalMoney = storageManager.getReceiptsByDate(tempDate).getNettCashSpent();
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr("The total amount of money spent on "
                + dateInLocal + " is $" + totalMoney
                + "\nNOTE : The date input is in the future");
    }

    /**
     * Function that outputs the expenditure for yesterday.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void expenditureForYesterday(StorageManager storageManager) throws DukeException {
        String dateYesterday = LocalDate.now().minusDays(1).toString();
        totalMoney = storageManager.getReceiptsByDate(dateYesterday).getNettCashSpent();
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr("The total amount of money spent yesterday "
                + "(" + dateYesterday + ") " + "is $" + totalMoney);
    }

    /**
     * Function that outputs the expenditure for today.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void expenditureForToday(StorageManager storageManager) throws DukeException {

        String dateToday = LocalDate.now().toString();
        totalMoney = storageManager.getReceiptsByDate(dateToday).getNettCashSpent();
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr("The total amount of money spent today "
                + "(" + dateToday + ") " + "is $" + totalMoney);
    }

    /**
     * Function that outputs the expenditure for the date given by user.
     * @param storageManager is the class that contains all the getter functions for the wallet
     * @throws DukeException is the error message
     */
    private void expenditureForDate(StorageManager storageManager) throws DukeException {
        totalMoney = storageManager.getReceiptsByDate(userDateInput).getNettCashSpent();
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr("The total amount of money spent on "
                + userDateInput + " is $" + totalMoney);
    }
}
