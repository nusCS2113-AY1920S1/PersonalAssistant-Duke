package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.gotoMarket;
    }
    @Override
    public void execute(Ui ui) {
        try {
            Simulation GotoMarketSimulation = new Simulation("GotoMarketSimulation", super.farmio);
            farmer.changeLocation("-Traveling-");
            GotoMarketSimulation.animate(1, 11);
            farmer.changeLocation("Market");
            GotoMarketSimulation.delayFrame(12, 1000);
            ui.typeWriter("You have arrived at the market");
        } catch (Exception e){
            e.getMessage();
        }
    }
}

