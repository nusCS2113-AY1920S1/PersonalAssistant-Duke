package UserCode.Actions;

import Places.*;
import UserInterfaces.Ui;
import org.json.simple.JSONObject;

public abstract class Action {
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;
    Market market;

    public Action() {
    }

    public Action(JSONObject obj) {
        this.wheatFarm = new WheatFarm((JSONObject) obj.get("farm_wheat"));
        this.chickenFarm = new ChickenFarm((JSONObject) obj.get("farm_chicken"));
        this.cowFarm = new CowFarm((JSONObject) obj.get("farm_cow"));
        this.market = new Market((Integer) obj.get("money"));
    }

    public Action(WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.market = market;
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

    public abstract void execute(Ui ui);

    public static boolean validateAction(String userInput) {
        for (ActionType type : ActionType.values()) {
            if (type.name().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        obj.put("money", market.toJSON());
        return obj;
    }
}
