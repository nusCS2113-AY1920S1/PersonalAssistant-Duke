/**
package Commands;
import Farmio.Farmer;
import Farmio.Storage;
import UserCode.Tasks.TaskList;
import UserCode.Actions.Action;
import UserCode.Actions.PlantSeedAction;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.IfTask;

public class TestCommand extends Command {

    public TestCommand(Ui ui, TaskList tasks, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market, ConditionChecker conditionChecker) {
        this.ui = ui;
        this.tasks = tasks;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.market = market;
        this.conditionChecker = conditionChecker;
=======
import UserCode.Conditions.Condition;
import UserCode.Tasks.Task;
import Farmio.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, Storage storage, Farmer farmer) {
        super(ui, storage, farmer);
>>>>>>> v2/FarmLogicV2
    }

    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
<<<<<<< HEAD
            Condition c = new BooleanCondition(BooleanConditionType.hasSeeds, conditionChecker);
            Action plantSeedAction = new PlantSeedAction(wheatFarm, chickenFarm, cowFarm, market);
            IfTask task1 = new IfTask(c, plantSeedAction);
            tasks.addTask(task1);
            Condition c2 = new MoneyCondition(Comparator.greaterThanOrEquals, 100, conditionChecker);
            Action buySeedAction = new BuySeedAction(wheatFarm, chickenFarm, cowFarm, market);
            IfTask task2 = new IfTask(c2, buySeedAction);
            tasks.addTask(task2);
=======
            Condition c = Condition.hasSeeds;
            Action plantSeedAction = new plantSeedAction(farmer.wheatFarm, farmer.chickenFarm, farmer.cowFarm);
            Task task = new Task(c, plantSeedAction);
            farmer.tasks.addTask(task);
>>>>>>> v2/FarmLogicV2
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
 **/
