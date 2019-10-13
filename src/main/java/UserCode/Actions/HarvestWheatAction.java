package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulations.Simulate;
import Simulations.Simulation;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public class HarvestWheatAction extends Action {
    int moneyChange = 0; //0 for all actions except sell

    public HarvestWheatAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public HarvestWheatAction(JSONObject obj){
        super(obj);
    }

    @Override
    public int execute(Ui ui) {
        try {
            wheatFarm.harvestWheat();
            new Simulate(ui, Simulation.HARVESTWHEAT).simulate();
        } catch (Exception e){
            e.getMessage();
        }
        return moneyChange;
    }

    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "harvest_wheat");
        return obj;
    }
}
