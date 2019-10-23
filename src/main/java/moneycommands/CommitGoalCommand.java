package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
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


public class CommitGoalCommand extends MoneyCommand {

    private String inputString;
    private DateTimeFormatter dateTimeFormatter;
    private ArrayList<Goal> goalsAfterCommit;
    private float goalSavingsAfterCommit;

    //@@author therealnickcheong
    public CommitGoalCommand(String cmd) {
        inputString = cmd;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");

    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws ParseException, DukeException {
        String[] args = inputString.split("commit goal ");
        if(args.length == 1){
            throw new DukeException("The Description of the command cannot be empty");
        }else{
            String combinedArgs = args[1];
            String[] indivArgs = combinedArgs.split(",");
            goalsAfterCommit =  new ArrayList<>(account.getShortTermGoals());
            goalSavingsAfterCommit = account.getGoalSavings();
            ArrayList<Integer> indexOfCommittedGoals = new ArrayList<>();
            for(String i: indivArgs){
                int index = Integer.parseInt(i.replaceAll("[^0-9]+", ""));
                indexOfCommittedGoals.add(index);
            }

            Collections.sort(indexOfCommittedGoals, Collections.reverseOrder());

            for(int j: indexOfCommittedGoals){
                if(j > account.getShortTermGoals().size()){
                    throw new DukeException("The serial number of the Goal is Out Of Bounds!");
                }else{
                    Goal committedGoal =  goalsAfterCommit.get(j-1);
                    float price = committedGoal.getPrice();
                    if(goalSavingsAfterCommit < price){
                        throw new DukeException("Goals committed exceeds Goal Savings");
                    }
                    goalsAfterCommit.remove(j-1);
                    goalSavingsAfterCommit -= price;
                }
            }

            //account.sortShortTermGoals(goalsAfterCommit);
            float savingsReqPerMonth = 0;
            for (int i = 1; i <= goalsAfterCommit.size();i++) {
                Goal currGoal = goalsAfterCommit.get(i-1);
                float currGoalPrice = currGoal.getPrice();
                LocalDate goalDate = currGoal.getDateBoughtDate();
                float monthsBetween = ChronoUnit.MONTHS.between(LocalDate.now(), goalDate);

                String goalProgress = "";

                if(goalSavingsAfterCommit >= currGoalPrice){
                    goalProgress = "[\u2713]";
                }else{
                    float percentageProgress = (goalSavingsAfterCommit/currGoalPrice)*100;
                    DecimalFormat df = new DecimalFormat("#.##");
                    df.setRoundingMode(RoundingMode.CEILING);
                    goalProgress = "[" + df.format(percentageProgress) + "%]";
                    savingsReqPerMonth += (currGoalPrice - goalSavingsAfterCommit)/monthsBetween;
                }

                ui.appendToOutput(" " + i + "." + goalProgress + goalsAfterCommit.get(i-1).toString() + "\n");
            }
            ui.appendToOutput("Goal Savings after commit: $" + goalSavingsAfterCommit + "\n");
            ui.appendToOutput("Target Savings for the Month: $" + savingsReqPerMonth + "\n");

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

