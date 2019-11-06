package usercode.actions;

import exceptions.FarmioFatalException;
import farmio.Farmer;
import farmio.Storage;
import frontend.Simulation;
import frontend.Ui;

public class GotoFarmAction extends Action {

    public GotoFarmAction() {
        super(ActionType.gotoWheatFarm);
    }

    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException {
        if (farmer.getLocation().equals("WheatFarm")) {
            simulation.simulate("GotoWheatFarmSimulation", 12);
            ui.typeWriter("You are already at the WheatFarm", false);
        } else {
            farmer.changeLocation("Traveling");
            simulation.simulate("GotoWheatFarmSimulation", 1, 11);
            farmer.changeLocation("WheatFarm");
            simulation.simulate("GotoWheatFarmSimulation", 12);
            ui.typeWriter("You have arrived at the WheatFarm", false);
        }
        ui.sleep(200);
    }
}