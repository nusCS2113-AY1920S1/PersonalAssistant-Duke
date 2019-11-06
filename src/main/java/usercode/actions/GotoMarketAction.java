package usercode.actions;

import exceptions.FarmioFatalException;
import farmio.Farmer;
import farmio.Storage;
import frontend.Simulation;
import frontend.Ui;

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
            simulation.simulate("GotoMarketSimulation", 12, 1);
            farmer.changeLocation("Market");
            simulation.simulate("GotoMarketSimulation", 1);
            ui.typeWriter("You have arrived at the market", false);
        }
        ui.sleep(200);
    }
}

