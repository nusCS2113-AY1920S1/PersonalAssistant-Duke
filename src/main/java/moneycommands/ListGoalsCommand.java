package moneycommands;

import controlpanel.MoneyStorage;
import controlpanel.DukeException;
import controlpanel.Ui;
import money.Account;
import money.Goal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.DecimalFormat;

/**
 * This command lists all short-term goals within the Short-Term Goal List to the user.
 */
public class ListGoalsCommand extends MoneyCommand {
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    //@@author therealnickcheong
    /**
     * Constructor of the list command.
     */
    public ListGoalsCommand() {
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * calculate the percentage progress the user has made towards their goals.
     * @param goalSavings current goal savings.
     * @param currGoalPrice price of the goal.
     * @return returns a percentage progress of the goal achieved.
     */
    public String percentageProgress(float goalSavings, float currGoalPrice) {
        float percentageProgress = (goalSavings / currGoalPrice) * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return "[" + df.format(percentageProgress) + "%]";
    }

    /**
     * rounds of a float to 2 decimal places.
     * @param f the float to rouond off.
     * @return returns the rounded off float.
     */
    public String dpRounding(float f) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(f);
    }

    /**
     * calculate the savings required by the user in the month per goal.
     * @param goalSavings current goal savings.
     * @param currGoalPrice the price of the goal.
     * @param monthsBetween months till the due date of the goal.
     * @return returns the amount to save per goal.
     */

    public float savingsPerGoal(float goalSavings, float currGoalPrice, float monthsBetween) {
        if (monthsBetween <= 0) {
            return currGoalPrice - goalSavings;
        } else {
            return (currGoalPrice - goalSavings) / monthsBetween;
        }
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
        float savingsReqPerMonth = 0;
        for (int i = 1; i <= account.getShortTermGoals().size();i++) {
            Goal currGoal = account.getShortTermGoals().get(i - 1);
            float currGoalPrice = currGoal.getPrice();
            LocalDate goalDate = currGoal.getDateBoughtDate();

            float monthsBetween = ChronoUnit.MONTHS.between(LocalDate.now(), goalDate);

            String goalProgress = "";
            if (account.getGoalSavings() >= currGoalPrice) {
                goalProgress = "[\u2713]";
            } else {
                goalProgress = percentageProgress(account.getGoalSavings(), currGoalPrice);
                savingsReqPerMonth += savingsPerGoal(account.getGoalSavings(), currGoalPrice, monthsBetween);
            }

            ui.appendToGraphContainer(" " + i + "." + goalProgress
                    + account.getShortTermGoals().get(i - 1).toString() + "\n");
        }
        String savingsPerMonth = dpRounding(savingsReqPerMonth);
        ui.appendToOutput("current Goal Savings: $" + decimalFormat.format(account.getGoalSavings()) + "\n");
        ui.appendToOutput("Target Savings for the Month: $" + decimalFormat.format(savingsReqPerMonth) + "\n");
        ui.appendToOutput("Got it, list will be printed in the other pane!\n");

    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
