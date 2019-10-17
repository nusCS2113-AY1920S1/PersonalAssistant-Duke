package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
    }
    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().plantSeeds();
            new Simulate(ui, "GotoMarketSimulation", 13).simulate(super.farmio);
        } catch (Exception e){
            e.getMessage();
        }
    }
}

