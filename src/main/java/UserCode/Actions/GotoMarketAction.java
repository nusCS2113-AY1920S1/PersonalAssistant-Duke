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
        final int NUMBER_OF_FRAMES = 3;
        try {
            Simulate GotoMarketSimulation = new Simulate("GotoMarketSimulation", super.farmio);
            farmer.changeLocation("-Traveling-");
            GotoMarketSimulation.simulate(1, 11);
            farmer.changeLocation("Market");
            GotoMarketSimulation.showFrame(11+1);
            ui.typeWriter("You have arrived at the market");
        } catch (Exception e){
            e.getMessage();
        }
    }
}
