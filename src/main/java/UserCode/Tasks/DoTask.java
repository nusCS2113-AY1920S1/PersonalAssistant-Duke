package UserCode.Tasks;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

public class DoTask extends Task {

    public DoTask(Condition condition, Action action) {
        super(condition, action, "do");
    }

    @Override
    public void execute(Farmio farmio) throws FarmioFatalException, FarmioException {
        {
            action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
        }
    }

    @Override
    public String toString() {
        return "do " + action.toString();
    }
}