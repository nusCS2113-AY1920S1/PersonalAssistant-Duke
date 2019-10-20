package UserCode.Actions;

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
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException {
        farmer.getWheatFarm().harvestWheat();
        Simulation.animate(ui, storage, farmer, "HarvestWheatSimulation", 0, 9);
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "harvest_wheat");
//        return obj;
//    }
}
