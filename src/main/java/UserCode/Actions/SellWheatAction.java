package UserCode.Actions;

import Farmio.Farmer;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import FrontEnd.Ui;
import org.json.simple.JSONObject;

public class SellWheatAction extends Action {

    public SellWheatAction(Farmer farmer) {
        super(farmer);
    }

    @Override
    public void execute(Ui ui) {
        farmer.getWheatFarm().buySeeds(); //TODO create wheatFarm.sellWheat()
        new Simulate(ui, "SellWheat", 10).simulate();
        ui.show("Selling wheat!");
    }

//    public JSONObject toJSON() {
//        JSONObject obj = super.toJSON();
//        obj.put("action", "selling_wheat");
//        return obj;
//    }
}
