package UserCode.Tasks;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Places.Farm;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class DoTask extends Task {

    public DoTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioFatalException, FarmioException {
        {
            action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer());
        }
    }

    @Override
    public String toString() {
        return "do " + action.toString();
    }
}