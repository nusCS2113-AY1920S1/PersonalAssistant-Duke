package UserCode.Actions;

import Farmio.Farmer;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import FrontEnd.Ui;
import org.json.simple.JSONObject;

public class PlantSeedAction extends Action {

    public PlantSeedAction(Farmer farmer) {
        super(farmer);
    }

    /*public PlantSeedAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().plantSeeds();
            new Simulate(ui, "PlantSeedSimulation", 10).simulate();
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
