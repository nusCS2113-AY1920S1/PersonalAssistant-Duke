package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class BuySeedAction extends Action {

    public BuySeedAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.buySeeds;
    }

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().buySeeds();
            farmer.changeMoney(-100);
            new Simulation("BuySeedSimulation", farmio).animate(0, 4);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
