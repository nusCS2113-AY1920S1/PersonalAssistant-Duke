package UserCode.Tasks;

import Farmio.Farmio;
import Places.Farm;
import UserCode.Actions.Action;
import UserCode.Conditions.BooleanCondition;
import UserCode.Conditions.BooleanConditionType;
import UserCode.Conditions.Condition;
import UserCode.Conditions.ConditionChecker;
import Farmio.Ui;

public class DoTask extends Task {

    public DoTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public void execute(Farmio farmio) {
        {
            action.execute(farmio.getUi());
        }
    }
}