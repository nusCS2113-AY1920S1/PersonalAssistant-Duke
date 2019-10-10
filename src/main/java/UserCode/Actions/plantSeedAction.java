package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Simulate.PlantSeedSimulation;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public class plantSeedAction extends Action {
    int moneyChange = 0; //0 for all actions except sell

    public plantSeedAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public plantSeedAction(JSONObject obj){
        super(obj);
    }

    @Override
    public int execute(Ui ui) {
        try {
            wheatFarm.plantSeeds();
            new PlantSeedSimulation(ui).simulate();
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
