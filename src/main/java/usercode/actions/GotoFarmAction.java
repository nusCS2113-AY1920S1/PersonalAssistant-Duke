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

    /**
     * Executes the action of going to the farm.
     * @param ui The user interface used to print messages of the action
     * @param storage which stores the assets after acton execution
     * @param farmer The farmer whose variables are displayed and changed
     * @param simulation The simulation object initialised with farmio
     * @throws FarmioFatalException if simulation file is missing
     */
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
