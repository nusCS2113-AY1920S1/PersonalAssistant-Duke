package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class GotoFarmAction extends Action {

    public GotoFarmAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.GOTO_WHEAT_FARM;
    }

    public void execute(Ui ui) {
        try {
            Simulation GotoWheatFarmSimulation = new Simulation("GotoWheatFarmSimulation", super.farmio);
            if (farmer.getLocation().equals("WheatFarm")) {
                GotoWheatFarmSimulation.delayFrame(12, 1000);
                ui.typeWriter("You are already at the WheatFarm");
                return;
            }
            farmer.changeLocation("Traveling");
            GotoWheatFarmSimulation.animate(1, 11);
            farmer.changeLocation("WheatFarm");
            GotoWheatFarmSimulation.delayFrame(12, 1000);
            ui.typeWriter("You have arrived at the WheatFarm");
        } catch (Exception e){
            e.getMessage();
        }
    }
}
