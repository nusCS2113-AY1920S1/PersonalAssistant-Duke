package UserCode.Tasks;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class IfTask extends Task {

    public IfTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException {
        {
            if (condition.check(farmio)) {
                action.execute(farmio.getUi());
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