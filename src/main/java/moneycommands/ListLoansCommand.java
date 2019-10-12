package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Loan;

import java.text.ParseException;

public class ListLoansCommand extends MoneyCommand {

    private String inputString;

    public ListLoansCommand(String command) {
        command = command.replaceFirst("list ", "");
        command = command.replace( "loans", "");
        inputString = command.replaceAll(" ", "");
    }

    @Override
    public boolean isExit() {
        return false;
    }

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
