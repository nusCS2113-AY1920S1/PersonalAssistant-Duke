package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Farmer;
import Farmio.Storage;
import FrontEnd.Simulation;
import FrontEnd.Ui;

public class SellWheatAction extends Action {

    public SellWheatAction() {
        this.type = ActionType.sellWheat;
    }

    @Override
    public void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException {
        farmer.getWheatFarm().buySeeds(); //TODO create wheatFarm.sellWheat()
        Simulation.animate(ui, storage, farmer, "SellWheat", 0, 7);
        ui.show("Selling wheat!");
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "selling_wheat");
//        return obj;
//    }
}
