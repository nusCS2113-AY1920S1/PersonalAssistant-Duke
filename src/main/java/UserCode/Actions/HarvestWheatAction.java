package UserCode.Actions;

import Farmio.Farmer;
import Places.*;
import Simulations.Simulate;
import FrontEnd.Ui;
import org.json.simple.JSONObject;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction(Farmer farmer) {
        super(farmer);
    }

    /*public HarvestWheatAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().harvestWheat();
            new Simulate(ui, "PlantSeed", 10).simulate();
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
