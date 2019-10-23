package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Expenditure;
import money.Goal;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DoneGoalCommand extends MoneyCommand {

    private String inputString;
    private DateTimeFormatter dateTimeFormatter;

    /**
     * Constructor of the command which initialises the add short-term goal command.
     * with the goal data within the user input.
     * @param cmd add command inputted from user.
     */
    //@@author therealnickcheong
    public DoneGoalCommand(String cmd) {
        inputString = cmd;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    @Override
    public boolean isExit() {
        return false;
    }


    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws ParseException, DukeException {


        String temp = inputString.replaceAll("[^0-9]", "");
        int indexNo = Integer.parseInt(temp);
        if (indexNo > account.getShortTermGoals().size()) {
            throw new DukeException("The serial number of the Goal is Out Of Bounds!");
        }

        Goal doneGoal =  account.getShortTermGoals().get(indexNo-1);
        float price = doneGoal.getPrice();
        String desc = doneGoal.getDescription();
        String category = doneGoal.getCategory();
        LocalDate doneDate = LocalDate.now();

        if(account.getGoalSavings() < price){
            throw new DukeException("Goal Price exceeds Goal Savings");
        }

        Expenditure e = new Expenditure(price, desc, category, doneDate);
        account.getExpListTotal().add(e);
        storage.markDeletedEntry("G", indexNo);
        account.getShortTermGoals().remove(indexNo-1);
        //account.sortShortTermGoals(account.getShortTermGoals());

        ui.appendToOutput(" Nice! This Goal is Completed:\n");
        ui.appendToOutput("  " + doneGoal.toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getShortTermGoals().size()) + " goals in the list.\n");
        //ui.appendToOutput("current Goal Savings: $" + account.getGoalSavings() + "\n");

        MoneyCommand list = new ListGoalsCommand();
        list.execute(account,ui,storage);
    }

    //remove from getExpListTotal, add back to getShortTermGoals()
    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        account.getExpListTotal().remove(account.getExpListTotal().size() - 1);
        String temp = inputString.replaceAll("[^0-9]", "");
        int indexNo = Integer.parseInt(temp);
        storage.undoDeletedEntry(account, "G", indexNo);
        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput(account.getShortTermGoals().get(indexNo - 1).toString() + " added to goals\n");
        ui.appendToOutput(" Now you have " + account.getShortTermGoals().size() + " goals listed\nand " +
                account.getExpListTotal().size() + "expenses listed\n");
    }
}
