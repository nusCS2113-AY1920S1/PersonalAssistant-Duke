package UserCode.Tasks;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

public class IfTask extends Task {

    public IfTask(Condition condition, Action action) {
        super(condition, action, "if");
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        {
            if (condition.check(farmio)) {
                action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
            } else {
                farmio.getUi().show("Condition not fulfilled, not executing task!");
            }
        }
    }

    @Override
    public String toString() {
        return "if " + super.toString();
    }
}