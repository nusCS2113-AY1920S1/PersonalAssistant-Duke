package UserCode.Actions;

import Farmio.Farmer;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import FrontEnd.Ui;

public class BuySeedAction extends Action {

    public BuySeedAction(Farmer farmer) {
        super(farmer);
    }

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().buySeeds();
            farmer.changeMoney(-100);
            new Simulate(ui, "BuySeedSimulation", 4).simulate();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
