package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class BuySeedAction extends Action {

    public BuySeedAction(Farmio farmio) {
        super(farmio);
    }

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().buySeeds();
            farmer.changeMoney(-100);
            new Simulate(ui, "BuySeedSimulation", 4).simulate(super.farmio);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
