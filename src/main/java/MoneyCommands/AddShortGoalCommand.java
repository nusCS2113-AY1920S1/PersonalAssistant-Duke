package MoneyCommands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Money.*;
import ControlPanel.*;

public class AddShortGoalCommand extends MoneyCommand {

    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public AddShortGoalCommand(String cmd){
        inputString = cmd;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, Storage storage) throws ParseException, DukeException {

                String desc = inputString.split("/amt ")[0].replaceFirst("goal-short ", "");
                float price = Float.parseFloat(inputString.split("/amt ")[1].split("/by ")[0]);
                Date byDate = simpleDateFormat.parse(inputString.split("/by ")[1].split("/priority ")[0] + "2359");
                String priorityLevel = inputString.split("/priority ")[1];
                String category = "GS";
                //System.out.println(priorityLevel);
                Goal g = new Goal(price, desc, category,byDate, priorityLevel);
                account.getShortTermGoals().add(g);

        System.out.println(" Got it. I've added this task: \n");
        System.out.println("     " + account.getShortTermGoals().get(account.getShortTermGoals().size()-1).toString() + "\n");
        System.out.println(" Now you have " + account.getShortTermGoals().size() + " Goals in the list.");
        System.out.println("Current Goal Savings: $" + account.getGoalSavings());
        ////storage.writeTheFile(account.getShortTermGoals());
    }
}
