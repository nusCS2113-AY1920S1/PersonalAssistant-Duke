package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;
import money.Income;
import money.Loan;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * This command settles the debt of an incoming/outgoing loan in the Loans List.
 */
public class SettleLoanCommand extends MoneyCommand {

    private String inputString;
    private static int serialNo;
    private static Loan.Type type;
    private static final int SETTLE_ALL_FLAG = -2;

    /**
     * Constructor of the command which initialises the settle loan command.
     * Determines the type of of loan to settle specified in the user input.
     * @param command Settle command inputted by user
     */
    //@@author chengweixuan
    public SettleLoanCommand(String command) {
        if (command.startsWith("paid")) {
            inputString = command.replaceFirst("paid ", "");
            type = Loan.Type.INCOMING;
        } else if (command.startsWith("received")) {
            inputString = command.replaceFirst("received ", "");
            type = Loan.Type.OUTGOING;
        } else {
            inputString = null;
            type = null;
        }
    }

    /**
     * This method checks if a String contains a numeric or non-numeric value.
     * @param checkStr String to be checked
     * @return True if the String is  numeric, else returns false
     */
    private boolean isNumeric(String checkStr) {
        try {
            int i = Integer.parseInt(checkStr);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * This method scans a given ArrayList of loans and returns an ArrayList
     * of all the names of the involved party in the loans.
     * @param loanList ArrayList of loans to be scanned
     * @return ArrayList of names of parties
     */
    private ArrayList<String> getListOfNames(ArrayList<Loan> loanList) {
        ArrayList<String> listOfNames = new ArrayList<>();
        for (Loan l : loanList) {
            listOfNames.add(l.getDescription());
        }
        return listOfNames;
    }

    /**
     * This method returns true if a person, who's name is specified, has a loan
     * listed within a given ArrayList of loans, else returns false
     * @param loanList ArrayList of loans to be checked
     * @param name String name of the person
     * @return True if the person is found in the loan list, else returns false
     */
    private boolean isInListOfNames(ArrayList<Loan> loanList, String name) {
        return getListOfNames(loanList).contains(name);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the settle loan command. According to the type of loan
     * to be settled specified by user, checks the incoming/outgoing loans for the
     * incoming/outgoing loan entry to be settled.
     * Searches for the loan specified by the user in either the incoming/outgoing
     * ArrayList of loans according the name or index, depending on user input, and
     * settles his/her loan with the amount inputted by the user.
     * If the amount repaid settles the entire debt, the loan is set as settled. Else,
     * the amount repaid is deducted from outstanding amount in the loan.
     * If an outgoing loan is settled, the debt repaid is entered into Total Income List.
     * If an incoming loan is settled, the debt repaid is entered into Total Expenditure List.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        String regex = type == Loan.Type.INCOMING ? " /to " : " /from ";
        String[] splitStr = inputString.split(regex, 2);
        float amount;
        if (splitStr[0].equals("all")) {
            amount = SETTLE_ALL_FLAG;
        } else {
            amount = Float.parseFloat(splitStr[0]);
        }

        if (isNumeric(splitStr[1])) {
            serialNo = Integer.parseInt(splitStr[1]) - 1;
        } else {
            if (type == Loan.Type.OUTGOING && isInListOfNames(account.getOutgoingLoans(), splitStr[1])) {
                serialNo = getListOfNames(account.getOutgoingLoans()).indexOf(splitStr[1]);
            } else if (type == Loan.Type.INCOMING && isInListOfNames(account.getIncomingLoans(), splitStr[1])) {
                serialNo = getListOfNames(account.getIncomingLoans()).indexOf(splitStr[1]);
            } else {
                throw new DukeException(splitStr[1] + " does not have a/an " + type.toString().toLowerCase() + " loan");
            }
        }

        Loan l;
        float amountToPrint;

        if (type == Loan.Type.INCOMING && serialNo > account.getIncomingLoans().size() ||
        type == Loan.Type.OUTGOING && serialNo > account.getOutgoingLoans().size() || serialNo < 0) {
            throw new DukeException("The serial number of the loan is Out Of Bounds!");
        }

        if (type == Loan.Type.OUTGOING &&
                amount <= account.getOutgoingLoans().get(serialNo).getOutstandingLoan()) {
            l = account.getOutgoingLoans().get(serialNo);
            amountToPrint = (amount == SETTLE_ALL_FLAG) ? l.getOutstandingLoan() : amount;
            l.settleLoanDebt(amount);
            account.getOutgoingLoans().set(serialNo, l);
            Income i = new Income(amount, "From " + l.getDescription(), Parser.shortcutTime("now"));
            account.getIncomeListTotal().add(i);
            account.getIncomeListCurrMonth().add(i);
        } else if (type == Loan.Type.INCOMING && amount <=
                account.getIncomingLoans().get(serialNo).getOutstandingLoan()) {
            l = account.getIncomingLoans().get(serialNo);
            amountToPrint = (amount == -2) ? l.getOutstandingLoan() : amount;
            l.settleLoanDebt(amount);
            account.getIncomingLoans().set(serialNo, l);
            Expenditure e = new Expenditure(amount, "To " + l.getDescription(), "Loan Repayment",
                    Parser.shortcutTime("now"));
            account.getExpListTotal().add(e);
            account.getExpListCurrMonth().add(e);
        } else {
            throw new DukeException("Whoa! The amount entered is more than debt! Type 'all' to settle the entire debt");
        }
        storage.writeToFile(account);

        String payDirection = (type == Loan.Type.INCOMING) ? " to " :
                type == Loan.Type.OUTGOING ? " from " : "";
        ui.appendToOutput(" Got it. An amount of $" + amountToPrint + " has been paid" + payDirection);
        ui.appendToOutput(l.getDescription() + " for the following loan: \n");
        ui.appendToOutput("     " + l.toString() + "\n");
        if (l.getStatus()) {
            ui.appendToOutput("The " + type.toString().toLowerCase() + " loan has been settled\n");
        }
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        /*
        String[] splitStr = inputString.split(" /to ", 2);
        //undo the expenditure income from total and current, flip settled to unsettled(def)
        Expenditure exp = account.getExpListTotal().get(account.getExpListTotal().size() - 1);
        float amount = -exp.getPrice();
        Loan l;
        switch (type) {
            case INCOMING:
                l = account.getIncomingLoans().get(serialNo);
                l.settleLoanDebt(amount);
                account.getIncomingLoans().set(serialNo, l);
                break;
            case OUTGOING:
                break;
            default:
                break;
        }
         */
        return;
    }
}
