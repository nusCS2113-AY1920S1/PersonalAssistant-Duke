package Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulate.PlantSeedSimulation;
import Simulate.Simulation;
import UserInterfaces.Ui;

public class plantSeedAction extends Action {
    int moneyChange = 0; //0 for all actions except sell
    Ui ui;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;

    public plantSeedAction(Ui ui, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.ui = ui;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    @Override
    public int execute() {
        try {
            wheatFarm.plantSeeds();
            new PlantSeedSimulation(ui).simulate();
        } catch (Exception e){
            e.getMessage();
        }
        return moneyChange;
    }
}
