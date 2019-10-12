package UserCode.Actions;

import Places.ChickenFarm;
import Places.CowFarm;
import Places.Farm;
import Places.WheatFarm;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public abstract class Action {
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;

    public Action() {
    }

    public Action(JSONObject obj) {
        this.wheatFarm = new WheatFarm((JSONObject) obj.get("farm_wheat"));
        this.chickenFarm = new ChickenFarm((JSONObject) obj.get("farm_chicken"));
        this.cowFarm = new CowFarm((JSONObject) obj.get("farm_cow"));
    }

    public Action(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
    }

    public WheatFarm extractWheatFarm() {
        return this.wheatFarm;
    }
    public ChickenFarm extractChickenFarm() {
        return this.chickenFarm;
    }
    public CowFarm extractCowFarm() {
        return this.cowFarm;
    }

    public abstract int execute(Ui ui);

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        return obj;
    }
}
