package UserCode.Actions;

import Farmio.Farmio;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction(Farmio farmio) {
        super(farmio);
        this.type = ActionType.harvestWheat;
    }

    /*public HarvestWheatAction(JSONObject obj){
        super(obj);
    }*/

    @Override
    public void execute(Ui ui) {
        try {
            farmer.getWheatFarm().harvestWheat();
            new Simulation("HarvestWheatSimulation", super.farmio).animate(0, 9);
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
