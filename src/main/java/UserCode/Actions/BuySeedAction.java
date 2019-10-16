package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import FrontEnd.Ui;

public class BuySeedAction extends Action {

    public BuySeedAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        super(wheatFarm, chickenFarm, cowFarm, market);
    }

    @Override
    public void execute(Ui ui) {
        try {
            wheatFarm.buySeeds();
            market.changeMoney(-100);
            new Simulate(ui, "BuySeedSimulation", 4).simulate();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
