package UserCode.Actions;

import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoFarmAction extends Action {

    public GotoFarmAction() {
        this.type = ActionType.gotoWheatFarm;
    }

    public void execute(Ui ui, Storage storage, Farmer farmer) {
        try {
            if (farmer.getLocation().equals("WheatFarm")) {
                Simulation.animate(ui, storage, farmer, 1000, "GotoWheatFarmSimulation", 12);
                ui.typeWriter("You are already at the WheatFarm");
                return;
            }
            farmer.changeLocation("Traveling");
            Simulation.animate(ui, storage, farmer, "GotoWheatFarmSimulation", 1, 11);
            farmer.changeLocation("WheatFarm");
            Simulation.animate(ui, storage, farmer, 1000, "GotoWheatFarmSimulation", 12);
            ui.typeWriter("You have arrived at the WheatFarm");
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
