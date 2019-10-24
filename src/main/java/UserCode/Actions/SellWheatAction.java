package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class SellWheatAction extends Action {

    public SellWheatAction() {
        super(ActionType.sellGrain);
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        if (!farmer.getWheatFarm().hasGrain() || !farmer.getLocation().equals("Market")) {
            farmer.setTaskFailed();
            simulation.animate("ErrorInExecution", 0);
            if (!farmer.getWheatFarm().hasGrain()) {
                ui.typeWriter("Error! you have attempted to sell grain despite not having any grain/\n");
            } else {
                ui.typeWriter("Error! you have attempted to sell grain despite not being at the market/\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            simulation.animate("SellWheatSimulation", 0, 6);
            ui.show("Selling grain!");
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
