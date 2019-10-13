package Commands;

import Places.Market;
import UserCode.Conditions.BooleanCondition;
import UserCode.Conditions.BooleanConditionType;
import UserCode.Conditions.Condition;
import UserCode.Conditions.ConditionChecker;
import UserCode.Actions.BuySeedAction;
import UserCode.Tasks.TaskList;
import UserCode.Actions.Action;
import UserCode.Actions.PlantSeedAction;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.IfTask;
import UserInterfaces.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, TaskList tasks, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market, ConditionChecker conditionChecker) {
        this.ui = ui;
        this.tasks = tasks;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.market = market;
        this.conditionChecker = conditionChecker;
    }
    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
            Condition c = new BooleanCondition(BooleanConditionType.hasSeeds, conditionChecker);
            Action plantSeedAction = new PlantSeedAction(wheatFarm, chickenFarm, cowFarm, market);
            IfTask task1 = new IfTask(c, plantSeedAction);
            tasks.addTask(task1);
          /**
            Condition c2 = Condition.hasMoney;
            Action buySeedAction = new BuySeedAction(wheatFarm, chickenFarm, cowFarm);
            IfTask task2 = new IfTask(c2, buySeedAction);
            tasks.addTask(task2);
            **/
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
