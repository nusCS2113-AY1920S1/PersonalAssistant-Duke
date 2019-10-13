package Commands;

import UserCode.Actions.buySeedAction;
import UserCode.Conditions.BooleanCondition;
import UserCode.Conditions.BooleanConditionType;
import UserCode.Conditions.ConditionChecker;
import UserCode.Tasks.TaskList;
import UserCode.Actions.Action;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Conditions.Condition;
import UserCode.Tasks.Task;
import UserInterfaces.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, TaskList tasks, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, ConditionChecker conditionChecker) {
        this.ui = ui;
        this.tasks = tasks;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.conditionChecker = conditionChecker;
    }
    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
            Condition c = new BooleanCondition(BooleanConditionType.hasSeeds, conditionChecker);
            Action buySeedAction = new buySeedAction(wheatFarm, chickenFarm, cowFarm);
            Task task = new Task(c, buySeedAction);
            tasks.addTask(task);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
