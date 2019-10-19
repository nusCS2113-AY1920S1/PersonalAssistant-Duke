package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class PlantSeedAction extends Action {

    public PlantSeedAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.plantSeeds;
    }

    /*public PlantSeedAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().plantSeeds();
            Simulation.animate(ui, farmio.getStorage(), farmio.getFarmer(), "PlantSeedSimulation", 0, 11);
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
