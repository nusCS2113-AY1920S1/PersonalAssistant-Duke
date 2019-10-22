package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Loan;

import java.text.ParseException;

/**
 * This command lists either the outgoing, incoming or all loans in the Loans List
 * according to user input.
 */
public class ListLoansCommand extends MoneyCommand {

    private String inputString;

    /**
     * Constructor of the list command. Isolates the command that determines what type
     * of loans to list.
     * @param command List command inputted from user
     */
    //@@ chengweixuan
    public ListLoansCommand(String command) {
        command = command.replaceFirst("list ", "");
        command = command.replace( "loans", "");
        inputString = command.replaceAll(" ", "");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method calculates the total monetary amount of a type of loan.
     * @param type Type of loan specified by user
     * @param account Account object containing all financial info of user saved on the programme
     * @return Float monetary amount of the type of loan
     */
    private float totalLoanAmount(Loan.Type type, Account account) {
        float total = 0;
        switch (type) {
        case OUTGOING:
            for (Loan l : account.getOutgoingLoans()) {
                total += l.getPrice();
            }
            break;
        case INCOMING:
            for (Loan l : account.getIncomingLoans()) {
                total += l.getPrice();
            }
            break;
        case ALL:
            for (Loan l : account.getLoans()) {
                total += l.getPrice();
            }
            break;
        default:
            total = -1;
            break;
        }
        return total;
    }

    /**
     * This method executes the list loans command.
     * Determines the type of loan to list and searches the Loan List for the type of
     * loan specified. Returns all loans of that type with the total monetary amount
     * of that type of loan and prints to user.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException if invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {

        Loan.Type searchType;
        switch (inputString) {
        case "incoming":
            searchType = Loan.Type.INCOMING;
            break;
        case "outgoing":
            searchType = Loan.Type.OUTGOING;
            break;
        case "all":
            searchType = Loan.Type.ALL;
            break;
        default:
            searchType = null;
            break;
        }

        int counter = 1;
        if (searchType == Loan.Type.INCOMING) {
            for (Loan l : account.getIncomingLoans()) {
                ui.appendToGraphContainer(" " + counter + "." + l.toString() + "\n");
                counter++;
            }
        } else if (searchType == Loan.Type.OUTGOING) {
            for (Loan l : account.getOutgoingLoans()) {
                ui.appendToGraphContainer(" " + counter + "." + l.toString() + "\n");
                counter++;
            }
        } else if (searchType == Loan.Type.ALL) {
            for (Loan l : account.getLoans()) {
                ui.appendToGraphContainer(" " + counter + "." + l.toString() + "\n");
                counter++;
            }
        } else {
            throw new DukeException("Oops! Type of list invalid! Please list either incoming, outgoing or all loans");
        }


        ui.appendToGraphContainer("Total amount of " + searchType.toString() + " Loans: $");
        ui.appendToGraphContainer(totalLoanAmount(searchType, account) + "\n");
        ui.appendToOutput("Got it! List of " + searchType.toString() + " Loans printed in the other pane! \n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {

    }
}
