package UserCode.Tasks;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Places.Farm;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class ForTask extends Task { //JX PLEASE FILL THIS SHIT UP

    public ForTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioFatalException, FarmioException { //if got error during the loop we use FarmioException to break out
        {
            int repeatNumber = 0;
            for (int i = 0; i < repeatNumber; i ++) {
                action.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer());
            }
        }
    }

    @Override
    public String toString() {
        return "for " + super.toString();
    }
}