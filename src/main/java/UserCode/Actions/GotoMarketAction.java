package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.GameConsole;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {
    public GotoMarketAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.gotoMarket;
    }
    @Override
    public void execute(Ui ui) {
        try {
            Simulate GotoMarketSimulation = new Simulate("GotoMarketSimulation", super.farmio);
            farmer.changeLocation("-Traveling-");
            GotoMarketSimulation.simulate(0, 1);
            farmer.changeLocation("Market");
            GotoMarketSimulation.showFrame(2);
            ui.typeWriter("You have arrived at the market");
        } catch (Exception e){
            e.getMessage();
        }
    }
}
