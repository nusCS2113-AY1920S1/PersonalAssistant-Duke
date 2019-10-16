package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import FrontEnd.Ui;
import org.json.simple.JSONObject;

public class SellWheatAction extends Action {

    public SellWheatAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.market = market;
    }

    @Override
    public void execute(Ui ui) {
        wheatFarm.buySeeds();
        new Simulate(ui, "SellWheat", 10).simulate();
        ui.show("Selling wheat!");
    }
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "selling_wheat");
        return obj;
    }
}
