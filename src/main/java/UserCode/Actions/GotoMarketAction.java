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
        try {
            if (farmer.getLocation().equals("Market")) {
                simulation.animate(1000, "GotoMarketSimulation", 12);
                ui.typeWriter("You are already at the market/");
                return;
            }
            farmer.changeLocation("Traveling");
            simulation.animate("GotoMarketSimulation", 1, 11);
            farmer.changeLocation("Market");
            simulation.animate(1000, "GotoMarketSimulation", 12);
            ui.typeWriter("You have arrived at the market/");
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

