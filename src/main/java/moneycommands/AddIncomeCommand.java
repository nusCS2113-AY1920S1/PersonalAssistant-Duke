package moneycommands;

import controlpanel.Parser;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import money.Account;
import money.Income;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This command adds an income source to the Total Income List.
 */
public class AddIncomeCommand extends MoneyCommand {

    private String inputString;

    /**
     * Constructor of the command which initialises the add income command
     * with the income source data within the user input.
     * @param command add command inputted from user
     */
    public AddIncomeCommand(String command) {
        inputString = command.replaceFirst("add income ", "");
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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/payday ", 2);
        float salary = Float.parseFloat(furSplit[0]);
        LocalDate payDay = Parser.shortcutTime(furSplit[1]);
        Income i = new Income(salary, description, payDay);
        account.getIncomeListTotal().add(i);
        storage.writeToFile(account);

        ui.appendToOutput(" Got it. I've added this income source: \n");
        ui.appendToOutput("     ");
        ui.appendToOutput(account.getIncomeListTotal().get(account.getIncomeListTotal().size() - 1).toString()
                + "\n");
        ui.appendToOutput(" Now you have " + account.getIncomeListTotal().size() + " income sources listed\n");
    }
}
