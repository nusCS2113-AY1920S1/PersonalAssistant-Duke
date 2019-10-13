package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulations.Simulate;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public class PlantSeedAction extends Action {
    int moneyChange = 0; //0 for all actions except sell

    public PlantSeedAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public PlantSeedAction(JSONObject obj){
        super(obj);
    }

    @Override
    public int execute(Ui ui) {
        try {
            wheatFarm.plantSeeds();
            new Simulate(ui, "PlantSeedSimulation", 10).simulate();
        } catch (Exception e){
            e.getMessage();
        }
        return moneyChange;
    }

    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "plant_seed");
        return obj;
    }
}
