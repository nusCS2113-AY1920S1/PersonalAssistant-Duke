package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
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
            new Simulate("BuySeedSimulation", farmio).simulate(0, 4);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
