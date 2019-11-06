package usercode.actions;

import exceptions.FarmioFatalException;
import farmio.Storage;
import exceptions.FarmioException;
import frontend.Simulation;
import frontend.Ui;
import farmio.Farmer;
import javafx.util.Pair;

import java.util.ArrayList;

public abstract class Action {

    ActionType type;

    public Action(ActionType type) {
        this.type = type;
    }

    /**
     * Executes the Action.
     *
     * @param ui The user interface used to print messages of the action
     * @param storage which stores the assets after acton execution
     * @param farmer The farmer whose variables are displayed and changed
     * @param simulation The simulation object initialised with farmio
     * @throws FarmioException if there is an error in executing the tasklist
     * @throws FarmioFatalException if file for simulation is missing
     */
    public abstract void execute(Ui ui, Storage storage, Farmer farmer, Simulation simulation)
            throws FarmioException, FarmioFatalException;

    /**
     * Checks if the user input String is a valid Action.
     *
     * @param userInput substring of the user input that represents the Action
     * @return true if it is a valid Action and false otherwise
     */
    public static boolean isValidAction(String userInput) {
        for (ActionType type : ActionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the user input String to an Action object.
     *
     * @param actionName the String that is the name of the Action Object
     * @return the Action Object
     * @throws FarmioException if the name is invalid
     */
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
     * Checks if Action criteria is met before execution.
     *
     * @param ui The user interface used to print messages of the action
     * @param farmer The farmer whose variables are displayed and changed
     * @param simulation The simulation object initialised with farmio
     * @param criteriaFeedbackList The list of criterias and their respective feedback messages
     * @throws FarmioException if there is an error in executing the tasklist
     * @throws FarmioFatalException if file for simulation is missing

     */
    protected void checkActionCriteria(Ui ui, Farmer farmer, Simulation simulation,
               ArrayList<Pair<Boolean, String>> criteriaFeedbackList) throws FarmioException, FarmioFatalException {
        boolean hasError = false;
        ui.sleep(2000);
        for (Pair<Boolean, String> criteriaFeedback: criteriaFeedbackList) {
            if (criteriaFeedback.getKey()) {
                if (!hasError) {
                    farmer.setTaskFailed();
                    simulation.simulate("ErrorInExecution", 1, 9);
                    ui.show(criteriaFeedback.getValue());
                    hasError = true;
                }
            }
        }
        if (hasError) {
            ui.typeWriter("",true);
            ui.getInput();
            throw new FarmioException("Task Error!");
        }
    }
}
