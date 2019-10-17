package UserCode.Actions;


import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Places.Market;
import FrontEnd.Ui;
import Farmio.Farmer;
import org.json.simple.JSONObject;

public abstract class Action {
    Farmer farmer;
    Farmio farmio;
    ActionType type;

    public Action() {}

    /**
    public Action(JSONObject obj) {
        this.wheatFarm = new WheatFarm((JSONObject) obj.get("farm_wheat"));
        this.chickenFarm = new ChickenFarm((JSONObject) obj.get("farm_chicken"));
        this.cowFarm = new CowFarm((JSONObject) obj.get("farm_cow"));
    }
    **/
    public Action(Farmio farmio) {
        this.farmer = farmio.getFarmer();
        this.farmio = farmio;
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

    public static Action stringToAction(String actionName, Farmio farmio) throws FarmioException {
        Action action;
        ActionType actionType = ActionType.buySeeds;
        for (ActionType a : ActionType.values()) {
            if ((a.name()).toLowerCase().equals(actionName)) {
                actionType = a;
            }
        }
        switch (actionType) {
            case buySeeds:
                return new BuySeedAction(farmio);
            case harvestWheat:
                return new HarvestWheatAction(farmio);
            case plantSeeds:
                return new PlantSeedAction(farmio);
            case sellWheat:
                return new SellWheatAction(farmio);
            case gotoMarket:
                return new GotoMarketAction(farmio);
            default:
            throw new FarmioException("Error Creating Action!");
        }
    }

    public String toString () {
        return type.name();
    }

    /*
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        obj.put("money", market.toJSON());
        return obj;
    }*/
}
