package moneycommands;

import controlpanel.Parser;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import money.Account;
import money.Income;
import money.Loan;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * This command adds an income source to the Total Income List.
 */
public class AddLoanCommand extends MoneyCommand {

    private String inputString;
    private Loan.Type type;

    /**
     * Constructor of the command which initialises the add income command
     * with the income source data within the user input.
     * @param command add command inputted from user
     */
    public AddLoanCommand(String command) {
        if (command.startsWith("lent")) {
            inputString = command.replaceFirst("lent ", "");
            type = Loan.Type.OUTGOING;
        } else if (command.startsWith("borrowed")) {
            inputString = command.replaceFirst("borrowed ", "");
            type = Loan.Type.INCOMING;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the add income command. Takes the input data from user and
     * adds an income source to the Total Income List.
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException if invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws ParseException, DukeException {
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/on ", 2);
        float amount = Float.parseFloat(furSplit[0]);
        LocalDate startDate = Parser.shortcutTime(furSplit[1]);
        Loan l = new Loan(amount, description, startDate, type);
        account.getLoans().add(l);
        //storage.writeToFile(account); figure this out later

        int loanTypeSize = l.getType() == Loan.Type.INCOMING ? account.getIncomingLoans().size() :
                l.getType() == Loan.Type.OUTGOING ? account.getOutgoingLoans().size() : -1;
        ui.appendToOutput(" Got it. I've added this " + l.getTypeString() + " Loan: \n");
        ui.appendToOutput("     ");
        ui.appendToOutput(account.getLoans().get(account.getLoans().size() - 1).toString()
                + "\n");
        ui.appendToOutput(" Now you have " + account.getLoans().size() + " loans listed");
        ui.appendToOutput(" and " + loanTypeSize + " " + l.getTypeString() + " Loans\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) {
//        int lastIndex = account.getIncomeListTotal().size() - 1;
//        Income i = account.getIncomeListTotal().get(lastIndex);
//        account.getIncomeListTotal().remove(i);
//        storage.writeToFile(account);
//
//        ui.appendToOutput(" Last command undone: \n");
//        ui.appendToOutput(i.toString() + "\n");
//        ui.appendToOutput(" Now you have " + account.getIncomeListTotal().size() + " income listed\n");
    }
}
