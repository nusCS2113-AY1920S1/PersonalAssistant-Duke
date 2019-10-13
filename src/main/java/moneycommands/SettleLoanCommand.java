package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Loan;

import java.text.ParseException;
import java.util.ArrayList;

public class SettleLoanCommand extends MoneyCommand {

    private String inputString;
    Loan.Type type;

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

    private boolean isNumeric(String checkStr) {
        try {
            int i = Integer.parseInt(checkStr);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    private ArrayList<String> getListOfNames(ArrayList<Loan> loanList) {
        ArrayList<String> listOfNames = new ArrayList<>();
        for (Loan l : loanList) {
            listOfNames.add(l.getDescription());
        }
        return listOfNames;
    }

    private boolean isInListOfNames(ArrayList<Loan> loanList, String name) {
        if (getListOfNames(loanList).contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        String[] splitStr = inputString.split(" /to ", 2);
        float amount;
        if (splitStr[0].equals("all")) {
            amount = -2;
        } else {
            amount = Float.parseFloat(splitStr[0]);
        }

        int serialNo;
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
        type == Loan.Type.OUTGOING && serialNo > account.getOutgoingLoans().size()) {
            throw new DukeException("The serial number of the loan is Out Of Bounds!");
        }

        if (type == Loan.Type.OUTGOING && amount <= account.getOutgoingLoans().get(serialNo).getOutstandingLoan()) {
            l = account.getOutgoingLoans().get(serialNo);
            amountToPrint = (amount == -2) ? l.getOutstandingLoan() : amount;
            l.settleLoanDebt(amount);
            account.getOutgoingLoans().set(serialNo, l);
        } else if (type == Loan.Type.INCOMING && amount <= account.getIncomingLoans().get(serialNo).getOutstandingLoan()) {
            l = account.getIncomingLoans().get(serialNo);
            amountToPrint = (amount == -2) ? l.getOutstandingLoan() : amount;
            l.settleLoanDebt(amount);
            account.getIncomingLoans().set(serialNo, l);
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
            ui.appendToOutput("The " + type.toString().toLowerCase() + " loan has been settled");
        }

    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {

    }
}
