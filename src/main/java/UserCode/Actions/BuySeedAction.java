package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulations.Simulate;
import Simulations.Simulation;
import UserCode.Actions.Action;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public class BuySeedAction extends Action {
    int moneyChange = -100;

    public BuySeedAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    @Override
    public int execute(Ui ui) {
        wheatFarm.buySeeds();
        new Simulate(ui, Simulation.BUYSEED).simulate();
        ui.show("Buying seeds!");
        return moneyChange;
    }
    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "buying_seeds");
        return obj;
    }
}
