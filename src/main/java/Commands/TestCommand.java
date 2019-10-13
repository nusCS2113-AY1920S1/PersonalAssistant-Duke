package Commands;

import UserCode.Actions.buySeedAction;
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
            Condition c = Condition.hasSeeds;
            Action buySeedAction = new buySeedAction(wheatFarm, chickenFarm, cowFarm);
            Task task = new Task(c, buySeedAction);
            tasks.addTask(task);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
