package moneycommands;

import controlpanel.Parser;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import controlpanel.DukeException;
import javafx.util.Pair;
import money.Account;
import money.Expenditure;
import money.Split;
import moneycommands.MoneyCommand;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This command adds a split expenditure to the Total Expenditure List.
 */
public class AddSplitCommand extends MoneyCommand {

    private String inputString;

    /**
     * Constructor of the command which initialises the add split expenditure command
     * with the split expenditure data within the user input.
     * @param command add command inputted from user
     */
    //@@ chengweixuan
    public AddSplitCommand(String command) {
        inputString = command.replaceFirst("split ", "");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the add split expenditure command. Takes the input data from user and
     * adds an split expenditure to the Total Expenditure List.
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
        String[] furSplit = splitStr[1].split("/cat ", 2);
        float price = Float.parseFloat(furSplit[0]);
        String[] morSplit = furSplit[1].split("/on ", 2);
        String category = morSplit[0];
        String[] evenMorSplit = morSplit[1].split("/with ", 2);
        LocalDate boughtTime = Parser.shortcutTime(evenMorSplit[0]);
        String[] people = evenMorSplit[1].split("and ");
        ArrayList<Pair<String, Boolean>> parties = new ArrayList<>();
        for (String person : people) {
            person = person.replaceAll(" ", "");
            Pair<String, Boolean> temp = new Pair<>(person, false);
            parties.add(temp);
        }
        Split s = new Split(price, description, category, boughtTime, parties);
        account.getExpListTotal().add(s);
        storage.writeToFile(account); // need to link this to storage later

        account.populateCurrentMonthLists();

        ui.appendToOutput(" Got it. I've added this split expenditure to your total spending: \n");
        ui.appendToOutput("     ");
        ui.appendToOutput(account.getExpListTotal().get(account.getExpListTotal().size() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) { // find out what this is for
        int lastIndex = account.getExpListTotal().size() - 1;
        Expenditure exp = account.getExpListTotal().get(lastIndex);
        account.getExpListTotal().remove(exp);
        storage.writeToFile(account);

        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput(exp.toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getExpListTotal().size() + " expenses listed\n");
    }
}
