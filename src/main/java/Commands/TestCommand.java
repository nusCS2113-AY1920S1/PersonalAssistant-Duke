package Commands;

import Actions.Action;
import Actions.plantSeedAction;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulate.PlantSeedSimulation;
import Task.Condition;
import Task.Task;
import UserInterfaces.Ui;

public class TestCommand extends Command {

    public TestCommand(Ui ui, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }
    @Override
    public void execute() throws FarmioException {
        try {
            Ui ui = new Ui();
            Condition c = Condition.hasSeeds;
            Action plantSeedAction = new plantSeedAction(ui, wheatFarm, chickenFarm, cowFarm);
            Task task = new Task(c, plantSeedAction);
            task.execute(); //should be adding it into the tasklist actually
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
