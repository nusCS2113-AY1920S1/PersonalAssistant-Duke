package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Ui;
import Farmio.Farmer;

public abstract class Action {

    ActionType type;

    /**
     * public Action(JSONObject obj) {
     * this.wheatFarm = new WheatFarm((JSONObject) obj.get("farm_wheat"));
     * this.chickenFarm = new ChickenFarm((JSONObject) obj.get("farm_chicken"));
     * this.cowFarm = new CowFarm((JSONObject) obj.get("farm_cow"));
     * }
     **/

    public abstract void execute(Ui ui, Storage storage, Farmer farmer) throws FarmioFatalException;

    public static boolean validateAction(String userInput) {
        for (ActionType type : ActionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    public static Action stringToAction(String actionName) throws FarmioException {
        ActionType actionType = ActionType.buySeeds;
        for (ActionType a : ActionType.values()) {
            if ((a.name()).toLowerCase().equals(actionName)) {
                actionType = a;
            }
        }
        switch (actionType) {
            case buySeeds:
                return new BuySeedAction();
            case harvestWheat:
                return new HarvestWheatAction();
            case plantSeeds:
                return new PlantSeedAction();
            case sellWheat:
                return new SellWheatAction();
            case gotoMarket:
                return new GotoMarketAction();
            default:
                throw new FarmioException("Error Creating Action!");
        }
    }

    public String toString() {
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
