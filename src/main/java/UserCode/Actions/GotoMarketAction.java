package UserCode.Actions;

import Farmio.Farmio;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.GOTO_MARKET;
    }
    @Override
    public void execute(Ui ui) {
        try {
            Simulation GotoMarketSimulation = new Simulation("GotoMarketSimulation", super.farmio);
            if (farmer.getLocation().equals("Market")) {
                GotoMarketSimulation.delayFrame(12, 1000);
                ui.typeWriter("You are already at the market");
                return;
            }
            farmer.changeLocation("Traveling");
            GotoMarketSimulation.animate(1, 11);
            farmer.changeLocation("Market");
            GotoMarketSimulation.delayFrame(12, 1000);
            ui.typeWriter("You have arrived at the market");
        } catch (Exception e){
            e.getMessage();
        }
    }
}

