package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.BankTracker;
import money.Income;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * This class create a bank account tracker for the user to track their
 * different bank account.
 */
public class CreateBankAccountCommand extends MoneyCommand {

    private BankTracker newTracker;

    //@@author cctt1014
    /**
     * The constructor analyzes the input command and get the information
     * (account description, initial amount, initial date and interest rate)
     * of the new bank account tracker and pack it as a new bank-tracker.
     * @param inputString The command line typed in by the user
     */
    public CreateBankAccountCommand(String inputString) throws DukeException {
        try {
            String desc = inputString.split(" /amt ")[0];
            String info = inputString.split(" /amt ")[1];
            desc = desc.replaceFirst("bank-account ","");

            String[] words = info.split(" ");
            LocalDate initialDate = Parser.shortcutTime(words[2]);
            if (Integer.parseInt(words[0]) < 0) {
                throw new DukeException("Sorry. The input initial balance should not be a negative number.");
            }
            newTracker = new BankTracker(desc, Integer.parseInt(words[0]),
                    initialDate, Double.parseDouble(words[4]));
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date! Please enter date in the format: d/m/yyyy\n");
        } catch (NumberFormatException e) {
            throw new DukeException("The format for the numbers is wrong, please check and type in again.\n");
        }


    }

    /**
     * This method labels whether this command means ceasing the overall program.
     * @return Whether this command means ceasing the overall program.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Since the new bank account tracker has been packed in the constructor,
     * in this method, Financial Ghost will append the information of the new
     * tracker to the list in the Account class and write the new information
     * into the local file for storage purpose. Finally, it will append a confirm
     * message to the output string.
     * @param account The class record all the financial information of the user
     * @param ui The user interface
     * @param storage The class used to store the information to the local disk
     * @throws DukeException The self-defined exception to handle the duplicate description
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        ArrayList<BankTracker> currList = account.getBankTrackerList();
        for (BankTracker b : currList) {
            if (b.getDescription().equals(newTracker.getDescription())) {
                throw new DukeException("There is a account with the same name! Please change the description!");
            }
        }
        account.getBankTrackerList().add(newTracker);
        Income newIncome = new Income(newTracker.getAmt(), "Initialize account: "
                + newTracker.getDescription(), newTracker.getLatestDate());
        account.getIncomeListTotal().add(newIncome);

        storage.writeToFile(account);
        ui.appendToOutput("New bank account tracker has been added to the list: \n");
        ui.appendToOutput(newTracker.getBankAccountInfo() + "\n");
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        int lastIndex = account.getBankTrackerList().size() - 1;
        BankTracker b = account.getBankTrackerList().get(lastIndex);
        account.getBankTrackerList().remove(b);
        storage.writeToFile(account);

        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput(b.toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getBankTrackerList().size()
                + " bank accounts listed\n");
    }
}