package UserCode.Actions;


import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Places.Market;
import FrontEnd.Ui;
import org.json.simple.JSONObject;

public abstract class Action {
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;
    Market market;

    public Action() {}

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
            if ((type.name()).toLowerCase() .equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    public static Action stringToAction(String actionName, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, Market market) {
        Action action;
        switch (actionName) {
            case "buySeeds":
                action = new BuySeedAction(wheatFarm, chickenFarm, cowFarm, market);
                break;
            case "harvestWheat":
                action = new HarvestWheatAction(wheatFarm, chickenFarm, cowFarm, market);
                break;
            case "plantSeeds":
                action =  new PlantSeedAction(wheatFarm, chickenFarm, cowFarm, market);
                break;
            case "sellWheat":
                action = new SellWheatAction(wheatFarm, chickenFarm, cowFarm, market);
                break;
            default:
                action = new EmptyAction();
        }
        return action;
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
