package UserCode.Actions;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class PlantSeedAction extends Action {

    public PlantSeedAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.PLANT_SEEDS;
    }

    /*public PlantSeedAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) throws FarmioException, FarmioFatalException {
        if (!farmer.getWheatFarm().hasSeeds() || !farmer.getLocation().equals("WheatFarm")) {
            farmio.getFarmer().setfailetask();
            new Simulation("ErrorInExecution", farmio).animate(0);
            if (!farmer.getWheatFarm().hasSeeds()) {
                ui.typeWriter("Error! you have attempted to plant seeds despite not having any seeds\n");
            } else {
                ui.typeWriter("Error! you have attempted to plant seeds despite not being at the Wheatfarm\n");
            }
            throw new FarmioException("Task Error!");
        }
        try {
            new Simulation("PlantSeedSimulation", super.farmio).animate(0, 10);
            farmer.getWheatFarm().plantSeeds();
            new Simulation("PlantSeedSimulation", super.farmio).delayFrame(11, 1000);
        } catch (Exception e){
            e.getMessage();
        }
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "plant_seed");
//        return obj;
//    }
}
