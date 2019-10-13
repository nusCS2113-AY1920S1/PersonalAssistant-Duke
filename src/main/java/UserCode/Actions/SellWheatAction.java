package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulations.Simulate;
import Simulations.Simulation;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public class SellWheatAction extends Action {
    int moneyChange = 10;

    public SellWheatAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    @Override
    public int execute(Ui ui) {
        wheatFarm.buySeeds();
        new Simulate(ui, Simulation.SELLWHEAT).simulate();
        ui.show("Selling wheat!");
        return moneyChange;
    }
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "selling_wheat");
        return obj;
    }
}
