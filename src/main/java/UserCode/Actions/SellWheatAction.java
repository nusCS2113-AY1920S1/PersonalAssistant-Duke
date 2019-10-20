package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class SellWheatAction extends Action {

    public SellWheatAction() {
        this.type = ActionType.sellWheat;
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException, FarmioException {
        if (!farmer.getWheatFarm().hasRipened() || !farmer.getLocation().equals("Market")) {
            farmer.setTaskFailed();
            Simulation.animate(ui, storage, farmer, "ErrorInExecution", 0);
            if (!farmer.getWheatFarm().hasWheat()) {
                ui.typeWriter("Error! you have attempted to sell wheat despite not having any wheat\n");
            } else {
                ui.typeWriter("Error! you have attempted to sell wheat despite not being at the market\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            Simulation.animate(ui, storage, farmer, "SellWheatSimulation", 0, 6);
            ui.show("Selling wheat!");
            farmer.earnMoney(farmer.getWheatFarm().sell());
            Simulation.animate(ui, storage, farmer, 1000, "SellWheatSimulation", 7);
        } catch (Exception e) {
            e.getMessage();
        }
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "selling_wheat");
//        return obj;
//    }
}
