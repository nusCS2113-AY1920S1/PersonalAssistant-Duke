package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import Simulations.Simulate;
import FrontEnd.Ui;
import org.json.simple.JSONObject;

public class HarvestWheatAction extends Action {

    public HarvestWheatAction(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public HarvestWheatAction(JSONObject obj){
        super(obj);
    }

    @Override
    public void execute(Ui ui) {
        try {
            wheatFarm.harvestWheat();
            new Simulate(ui, "PlantSeed", 10).simulate();
        } catch (Exception e){
            e.getMessage();
        }
    }

    public JSONObject toJSON() {
        JSONObject obj = super.toJSON();
        obj.put("action", "harvest_wheat");
        return obj;
    }
}
