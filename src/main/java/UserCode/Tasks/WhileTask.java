package UserCode.Tasks;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class WhileTask extends Task {

    public WhileTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException { //if got error during the loop we use FarmioException to break out
        while (checkCondition(farmio)) {
            action.execute(farmio.getUi());
        }
    }
}