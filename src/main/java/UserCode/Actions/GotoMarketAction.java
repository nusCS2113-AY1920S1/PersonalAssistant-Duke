package UserCode.Actions;

import Farmio.Farmio;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.gotoMarket;
    }
    @Override
    public void execute(Ui ui) {
        Storage storage = farmio.getStorage();
        try {
            farmer.changeLocation("-Traveling-");
            Simulation.animate(ui, storage, farmio.getFarmer(), "GotoMarketSimulation", 1, 11);
            farmer.changeLocation("Market");
            Simulation.animate(ui, storage, farmio.getFarmer(), "GotoMarketSimulation", 12, 1000);
            ui.typeWriter("You have arrived at the market");
        } catch (Exception e){
            e.getMessage();
        }
    }
}

