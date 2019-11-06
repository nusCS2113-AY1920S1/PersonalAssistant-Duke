package usercode.tasks;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import exceptions.FarmioException;
import usercode.actions.Action;
import usercode.conditions.Condition;

public class IfTask extends Task {

    /**
     * Creates a task of type if.
     * @param condition The condition to be considered.
     * @param action The action to be executed if the condition is true.
     */
    public IfTask(Condition condition, Action action) {
        super(taskType.IF, condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        {
            if (condition.check(farmio)) {
                action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
            } else {
                farmio.getSimulation().simulate();
                farmio.getUi().show("Condition not fulfilled, not executing task!");
                farmio.getUi().sleep(1000);
            }
        }
    }

    @Override
    public String toString() {
        return "if " + super.toString();
    }
}