package Commands;

import Farmio.Farmer;
import Farmio.Storage;
import UserCode.Tasks.TaskList;
import UserCode.Actions.Action;
import UserCode.Actions.plantSeedAction;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Conditions.Condition;
import UserCode.Tasks.Task;
import Farmio.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, Storage storage, Farmer farmer) {
        super(ui, storage, farmer);
    }

    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
            Condition c = Condition.hasSeeds;
            Action plantSeedAction = new plantSeedAction(farmer.wheatFarm, farmer.chickenFarm, farmer.cowFarm);
            Task task = new Task(c, plantSeedAction);
            farmer.tasks.addTask(task);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
