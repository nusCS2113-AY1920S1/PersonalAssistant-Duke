package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Farmio.Farmer;
import org.json.simple.JSONObject;

public abstract class Action {

    ActionType type;

    public Action(ActionType type){
        this.type = type;
    }

    /**
     * public Action(JSONObject obj) {
     * this.wheatFarm = new WheatFarm((JSONObject) obj.get("farm_wheat"));
     * this.chickenFarm = new ChickenFarm((JSONObject) obj.get("farm_chicken"));
     * this.cowFarm = new CowFarm((JSONObject) obj.get("farm_cow"));
     * }
     **/

    public abstract void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation) throws FarmioException, FarmioFatalException;

    public static boolean validateAction(String userInput) {
        for (ActionType type : ActionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    public static Action toAction(String actionName) throws FarmioException {
        ActionType actionType = ActionType.buySeeds;
        for (ActionType a : ActionType.values()) {
            if (a.name().equalsIgnoreCase(actionName)) {
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
            case sellGrain:
                return new SellWheatAction();
            case gotoMarket:
                return new GotoMarketAction();
            case gotoWheatFarm:
                return new GotoFarmAction();
            default:
                throw new FarmioException("Error Creating Action!");
        }
    }

    public String toString() {
        return type.name();
    }

}
