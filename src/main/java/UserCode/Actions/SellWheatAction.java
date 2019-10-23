package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class SellWheatAction extends Action {

    public SellWheatAction() {
        super(ActionType.sellWheat);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        if (!farmer.getWheatFarm().hasRipened() || !farmer.getLocation().equals("Market")) {
            farmer.setTaskFailed();
            simulation.animate("ErrorInExecution", 0);
            if (!farmer.getWheatFarm().hasWheat()) {
                ui.typeWriter("Error! you have attempted to sell wheat despite not having any wheat/\n");
            } else {
                ui.typeWriter("Error! you have attempted to sell wheat despite not being at the market/\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            simulation.animate("SellWheatSimulation", 0, 6);
            ui.show("Selling wheat!");
            farmer.earnMoney(farmer.getWheatFarm().sell());
            simulation.animate(1000, "SellWheatSimulation", 7);
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
