package UserCode.Actions;

import Exceptions.FarmioFatalException;
import Farmio.Storage;
import Exceptions.FarmioException;
import FrontEnd.Simulation;
import FrontEnd.Ui;
import Farmio.Farmer;
import javafx.util.Pair;

import java.util.ArrayList;

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

    /**
     * Executes the Action
     * @param ui The user interface used to print messages of the action
     * @param storage which stores the assets after acton execution
     * @param farmer The farmer whose variables are displayed and changed
     * @param simulation The simulation object initialised with farmio
     * @throws FarmioException if there is an error in executing the tasklist
     * @throws FarmioFatalException if file for simulation is missing
     */
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

    /**
     * Checks if Action criteria is met before execution
     * @param ui The user interface used to print messages of the action
     * @param farmer The farmer whose variables are displayed and changed
     * @param simulation The simulation object initialised with farmio
     * @param criteriaFeedbackList The list of criterias and their respective feedback messages
     * @throws FarmioException if there is an error in executing the tasklist
     * @throws FarmioFatalException if file for simulation is missing

     */
    protected void checkActionCriteria(Ui ui, Farmer farmer, Simulation simulation
            , ArrayList<Pair<Boolean, String>> criteriaFeedbackList) throws FarmioException, FarmioFatalException {
        boolean hasError = false;
        ui.sleep(2000);
        for (Pair<Boolean, String> criteriaFeedback: criteriaFeedbackList) {
            if (criteriaFeedback.getKey()) {
                if (!hasError) {
                    farmer.setTaskFailed();
                    simulation.simulate("ErrorInExecution", 0);
                    hasError = true;
                }
                ui.typeWriter(criteriaFeedback.getValue(), false);
            }
        }
        if (hasError) {
            ui.sleep(2000);
            throw new FarmioException("Task Error!");
        }
    }
}
