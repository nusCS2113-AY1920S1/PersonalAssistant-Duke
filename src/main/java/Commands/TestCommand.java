package Commands;

import UserCode.Actions.BuySeedAction;
import UserCode.Tasks.TaskList;
import UserCode.Actions.Action;
import UserCode.Actions.PlantSeedAction;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Conditions.Condition;
import UserCode.Tasks.IfTask;
import UserInterfaces.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, TaskList tasks, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.tasks = tasks;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }
    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
            Condition c1 = Condition.hasSeeds;
            Action plantSeedAction = new PlantSeedAction(wheatFarm, chickenFarm, cowFarm);
            IfTask task1 = new IfTask(c1, plantSeedAction);
            tasks.addTask(task1);
            Condition c2 = Condition.hasMoney;
            Action buySeedAction = new BuySeedAction(wheatFarm, chickenFarm, cowFarm);
            IfTask task2 = new IfTask(c2, buySeedAction);
            tasks.addTask(task2);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
