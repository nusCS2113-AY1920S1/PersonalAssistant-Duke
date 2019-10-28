package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoMarketAction extends Action {

    public GotoMarketAction() {
        super(ActionType.gotoMarket);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException {
        if (farmer.getLocation().equals("Market")) {
            simulation.simulate("GotoMarketSimulation", 12);
            ui.typeWriter("You are already at the market", false);
        } else {
            farmer.changeLocation("Traveling");
            simulation.simulate("GotoMarketSimulation", 1, 11);
            farmer.changeLocation("Market");
            simulation.simulate("GotoMarketSimulation", 12);
            ui.typeWriter("You have arrived at the market", false);
        }
        ui.sleep(1000);
    }
}

