package UserCode.Tasks;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

public class ForTask extends Task { //JX PLEASE FILL THIS SHIT UP

    public ForTask(Condition condition, Action action) {
        super(condition, action, "for");
    }

    @Override
    public void execute(Farmio farmio) throws FarmioFatalException, FarmioException { //if got error during the loop we use FarmioException to break out
        {
            int repeatNumber = 0;
            for (int i = 0; i < repeatNumber; i ++) {
                action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
            }
        }
    }

    @Override
    public String toString() {
        return "for " + super.toString();
    }
}