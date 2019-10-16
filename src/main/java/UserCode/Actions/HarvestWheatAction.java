package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulate;
import FrontEnd.Ui;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction(Farmio farmio) {
        super(farmio);
    }

    /*public HarvestWheatAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().harvestWheat();
            new Simulate(ui, "PlantSeed", 10).simulate(super.farmio);
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
