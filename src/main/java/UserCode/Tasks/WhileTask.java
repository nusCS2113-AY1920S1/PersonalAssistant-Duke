package UserCode.Tasks;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

public class WhileTask extends Task {

    public WhileTask(Condition condition, Action action) {
        super(Type.WHILE, condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException { //if got error during the loop we use FarmioException to break out
        while (checkCondition(farmio)) {
            action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
        }
    }
}