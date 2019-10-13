package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulations.Simulate;
import UserInterfaces.Ui;

public class buySeedAction extends Action {
    private int moneyChange = -100;

    public buySeedAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    @Override
    public int execute(Ui ui) {
        try {
            wheatFarm.buySeeds();
            new Simulate(ui, "BuySeedSimulation", 4).simulate();
        } catch (Exception e) {
            e.getMessage();
        }
        return moneyChange;
    }
}
