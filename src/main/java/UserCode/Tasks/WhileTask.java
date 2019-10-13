package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserInterfaces.Ui;

public class WhileTask extends Task {

    public WhileTask(Condition condition, Action action) {
        super(condition, action);
    }

    @Override
    public int execute(Ui ui) { //if got error during the loop we use FarmioException to break out
        int moneyChange = 0;
        while (checkCondition()) {
            moneyChange += action.execute(ui);
        }
            return moneyChange;
    }
}