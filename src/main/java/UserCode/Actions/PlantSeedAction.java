package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class PlantSeedAction extends Action {

    public PlantSeedAction() {
        this.type = ActionType.plantSeeds;
    }

    /*public PlantSeedAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioFatalException, FarmioException {
        if (!farmer.getWheatFarm().hasSeeds() || !farmer.getLocation().equals("WheatFarm")) {
            farmer.setTaskFailed();
            simulation.animate("ErrorInExecution", 0);
            if (!farmer.getWheatFarm().hasSeeds()) {
                ui.typeWriter("Error! you have attempted to plant seeds despite not having any seeds\n");
            } else {
                ui.typeWriter("Error! you have attempted to plant seeds despite not being at the Wheatfarm\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            simulation.animate("PlantSeedSimulation", 0, 10);
            farmer.getWheatFarm().plantSeeds();
            simulation.animate(1000, "PlantSeedSimulation", 11);
        } catch (Exception e) {
            e.getMessage();
        }
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "plant_seed");
//        return obj;
//    }
}
