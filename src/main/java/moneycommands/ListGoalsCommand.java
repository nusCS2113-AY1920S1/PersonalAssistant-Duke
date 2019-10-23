package moneycommands;

import controlpanel.*;
import money.Account;
import money.Goal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This command lists all short-term goals within the Short-Term Goal List to the user
 */
public class ListGoalsCommand extends MoneyCommand{

    /**
     * Constructor of the list command
     */
    //@@ therealnickcheong
    public ListGoalsCommand(){
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the list goals command.
     * Displays all short-term goals in the Short-Term Goals List to the user according to index
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) {
        //account.sortShortTermGoals(account.getShortTermGoals());
        float savingsReqPerMonth = 0;
        for (int i = 1; i <= account.getShortTermGoals().size();i++) {
            Goal currGoal = account.getShortTermGoals().get(i-1);
            float currGoalPrice = currGoal.getPrice();
            LocalDate goalDate = currGoal.getDateBoughtDate();

            float monthsBetween = ChronoUnit.MONTHS.between(LocalDate.now(), goalDate);

            String goalProgress = "";
            if(account.getGoalSavings() >= currGoalPrice){
                goalProgress = "[\u2713]";
            }else{
                float percentageProgress = (account.getGoalSavings()/currGoalPrice)*100;
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);
                goalProgress = "[" + df.format(percentageProgress) + "%]";
                savingsReqPerMonth += (currGoalPrice - account.getGoalSavings())/monthsBetween;
            }

            ui.appendToGraphContainer(" " + i + "." + goalProgress + account.getShortTermGoals().get(i-1).toString() + "\n");
        }
        //System.out.println("current Goal Savings: $" + account.getGoalSavings());
        ui.appendToOutput("current Goal Savings: $" + account.getGoalSavings() + "\n");
        ui.appendToOutput("Target Savings for the Month: $" + savingsReqPerMonth + "\n");
        ui.appendToOutput("Got it, list will be printed in the other pane!\n");

    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
