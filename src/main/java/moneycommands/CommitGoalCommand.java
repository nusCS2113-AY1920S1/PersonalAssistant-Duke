package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Goal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;


public class CommitGoalCommand extends MoneyCommand {

    private String inputString;
    private DateTimeFormatter dateTimeFormatter;
    private ArrayList<Goal> goalsAfterCommit;
    private float goalSavingsAfterCommit;
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private static final int TICK_NO = 0x2713;



    //@@author therealnickcheong

    /**
     * Constructor of the command which initialises the commit goal command.
     * @param cmd command input by the user.
     */

    public CommitGoalCommand(String cmd) {
        inputString = cmd;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");

    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * method to calculate the percentage progress the user has made towards his goals.
     * @param goalSavings current goal savings.
     * @param currGoalPrice the price of the goal to calculate.
     * @return String of the percentage progress.
     */

    public String percentageProgress(float goalSavings, float currGoalPrice) {
        float percentageProgress = (goalSavings / currGoalPrice) * 100;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return "[" + df.format(percentageProgress) + "%]";
    }

    /**
     * method to calculate the savings per month to achieve the set goal.
     * @param goalSavings current goal savings.
     * @param currGoalPrice price of the goal.
     * @param monthsBetween months left to achieve the goal.
     * @return the amount of  money to save per month for the goal.
     */

    public float savingsPerGoal(float goalSavings, float currGoalPrice, float monthsBetween) {
        if (monthsBetween <= 0) {
            return currGoalPrice - goalSavings;
        } else {
            return (currGoalPrice - goalSavings) / monthsBetween;
        }
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws ParseException, DukeException {
        String[] args = inputString.split("commit goal ");
        if (args.length == 1) {
            throw new DukeException("Please enter in the format: "
                    + "commit goal <index 1, index 2,...>");
        } else {
            String combinedArgs = args[1];
            String[] indivArgs = combinedArgs.split(",");
            goalsAfterCommit =  new ArrayList<>(account.getShortTermGoals());
            goalSavingsAfterCommit = account.getGoalSavings();
            ArrayList<Integer> indexOfCommittedGoals = new ArrayList<>();
            try {
                for (String i: indivArgs) {
                    int index = Integer.parseInt(i.replaceAll("[^-?0-9]+", ""));
                    indexOfCommittedGoals.add(index);
                }
            } catch (NumberFormatException e) {
                throw new DukeException("The indexes must be a number");
            }

            Set<Integer> noDuplicateSet = new HashSet<>(indexOfCommittedGoals);
            indexOfCommittedGoals.clear();
            indexOfCommittedGoals.addAll(noDuplicateSet);
            Collections.sort(indexOfCommittedGoals, Collections.reverseOrder());


            for (int j: indexOfCommittedGoals) {
                if (j > account.getShortTermGoals().size() || (j < 1)) {
                    throw new DukeException("The serial number of the Goal is Out Of Bounds!");
                } else {
                    Goal committedGoal =  goalsAfterCommit.get(j - 1);
                    float price = committedGoal.getPrice();
                    if (goalSavingsAfterCommit < price) {
                        throw new DukeException("Goals committed exceeds Goal Savings");
                    }
                    goalsAfterCommit.remove(j - 1);
                    goalSavingsAfterCommit -= price;
                }
            }

            float savingsReqPerMonth = 0;
            for (int i = 1; i <= goalsAfterCommit.size();i++) {
                Goal currGoal = goalsAfterCommit.get(i - 1);
                float currGoalPrice = currGoal.getPrice();
                LocalDate goalDate = currGoal.getDateBoughtDate();
                float monthsBetween = ChronoUnit.MONTHS.between(LocalDate.now(), goalDate);

                String goalProgress = "";

                if (goalSavingsAfterCommit >= currGoalPrice) {
                    goalProgress = "[" + Character.toString((char)TICK_NO) + "]";
                } else {
                    goalProgress = percentageProgress(goalSavingsAfterCommit, currGoalPrice);
                    savingsReqPerMonth += savingsPerGoal(goalSavingsAfterCommit, currGoalPrice, monthsBetween);
                }

                ui.appendToOutput(" " + i + "." + goalProgress + goalsAfterCommit.get(i - 1).toString() + "\n");
            }
            ui.appendToOutput("Goal Savings after commit: $" + decimalFormat.format(goalSavingsAfterCommit) + "\n");
            ui.appendToOutput("Target Savings for the Month after commit: $"
                    + decimalFormat.format(savingsReqPerMonth) + "\n");

            MoneyCommand list = new ListGoalsCommand();
            list.execute(account,ui,storage);
        }
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}

