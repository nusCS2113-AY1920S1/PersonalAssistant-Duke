package usercode.tasks;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import exceptions.FarmioException;
import usercode.actions.Action;
import usercode.conditions.Condition;

public class WhileTask extends Task {

    /**
     * Creates a Task of type while.
     *
     * @param condition The condition to be considerd
     * @param action The action to be executed while the condition is true
     */
    public WhileTask(Condition condition, Action action) {
        super(Tasktype.WHILE, condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        while (checkCondition(farmio)) {
            action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
        }
    }
}