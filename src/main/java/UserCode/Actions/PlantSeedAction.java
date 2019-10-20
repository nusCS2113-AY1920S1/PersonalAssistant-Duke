package UserCode.Actions;

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
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException {
        farmer.getWheatFarm().plantSeeds();
        Simulation.animate(ui, storage, farmer, "PlantSeedSimulation", 0, 11);
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "plant_seed");
//        return obj;
//    }
}
