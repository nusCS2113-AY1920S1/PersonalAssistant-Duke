package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction() {
        this.type = ActionType.harvestWheat;
    }

    /*public HarvestWheatAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        if (!farmer.getWheatFarm().hasWheat() || !farmer.getLocation().equals("WheatFarm")) {
            farmer.setTaskFailed();
            simulation.animate("ErrorInExecution", 0);
            if (!farmer.getWheatFarm().hasWheat()) {
                ui.typeWriter("Error! you have attempted to harvest wheat despite not having any seedlings/\n");
            } else {
                ui.typeWriter("Error! you have attempted to harvest wheat despite not being at the Wheatfarm/\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            simulation.animate("HarvestWheatSimulation", 0, 8);
            farmer.getWheatFarm().harvestWheat();
            simulation.animate(1000,"HarvestWheatSimulation", 9);
        } catch (Exception e){
            e.getMessage();
        }
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "harvest_wheat");
//        return obj;
//    }
}
