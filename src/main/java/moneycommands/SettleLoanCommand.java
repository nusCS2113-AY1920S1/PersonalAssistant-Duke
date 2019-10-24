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
    private float amount;
    private static int serialNo;
    private static Loan.Type type;
    private static final int SETTLE_ALL_FLAG = -2;
    private String description;
    private String loanToString;
    private boolean isSettled;
    private String payDirection;

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
     * This method returns the index of the loan becoming to the person whose name
     * is specified. If the loan is not found, throws a DukeException.
     * @param loanList ArrayList of loans to be checked
     * @param name String name of the person
     * @return Integer index of the loan
     * @throws DukeException When loan is not found
     */
    private int getSerialNo(ArrayList<Loan> loanList, String name) throws DukeException {
        if (getListOfNames(loanList).contains(name)) {
            return getListOfNames(loanList).indexOf(name);
        } else {
            throw new DukeException(name + " does not have a/an " + type.toString().toLowerCase() + " loan");
        }
    }

    /**
     * This method settles the debt of a loan.
     * Sets loan to settled if the entire debt is paid.
     * @param loanList ArrayList of loans containing the loan
     * @param serialNo Integer index of the loan
     * @param amount Float amount of debt to be settled
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the amount is greater than the outstanding debt
     */
    private void setLoanToSettled(ArrayList<Loan> loanList, int serialNo, float amount) throws ParseException, DukeException {
        if (amount > loanList.get(serialNo).getOutstandingLoan()) {
            throw new DukeException("Whoa! The amount entered is more than debt! Type 'all' to settle the entire debt\n");
        }
        Loan l = loanList.get(serialNo);
        amount = (amount == SETTLE_ALL_FLAG) ? l.getOutstandingLoan() : amount;
        l.settleLoanDebt(amount);
        description = l.getDescription();
        loanToString = l.toString();
        isSettled = l.getStatus();
        loanList.set(serialNo, l);
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
        try {
            String regex = type == Loan.Type.INCOMING ? " /to " : " /from ";
            String[] splitStr = inputString.split(regex, 2);
            if (splitStr[0].equals("all")) {
                amount = SETTLE_ALL_FLAG;
            } else {
                amount = Float.parseFloat(splitStr[0]);
            }
            if (Parser.isNumeric(splitStr[1])) {
                serialNo = Integer.parseInt(splitStr[1]) - 1;
            } else {
                if (type == Loan.Type.OUTGOING) {
                    serialNo = getSerialNo(account.getOutgoingLoans(), splitStr[1]);
                } else if (type == Loan.Type.INCOMING) {
                    serialNo = getSerialNo(account.getIncomingLoans(), splitStr[1]);
                }
            }
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter the amount in numbers!\n");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Please enter in the format: paid/received <amount> /(to/from) <person/serialNo>\n");
        }

        try {
            if (type == Loan.Type.OUTGOING) {
                payDirection = " from ";
                setLoanToSettled(account.getOutgoingLoans(), serialNo, amount);
                Income i = new Income(amount, "From " + description, Parser.shortcutTime("now"));
                account.getIncomeListTotal().add(i);
            } else if (type == Loan.Type.INCOMING) {
                payDirection = " to ";
                setLoanToSettled(account.getIncomingLoans(), serialNo, amount);
                Expenditure e = new Expenditure(amount, "To " + description, "Loan Repayment",
                        Parser.shortcutTime("now"));
                account.getExpListTotal().add(e);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("The serial number of the loan is Out Of Bounds!\n");
        }
        storage.writeToFile(account);

        ui.appendToOutput(" Got it. An amount of $" + amount + " has been paid" + payDirection);
        ui.appendToOutput(description + " for the following loan: \n");
        ui.appendToOutput("     " + loanToString + "\n");
        if (isSettled) {
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
