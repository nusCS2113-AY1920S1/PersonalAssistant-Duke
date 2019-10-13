package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserCode.Conditions.ConditionChecker;
import UserInterfaces.Ui;

public class IfElseTask extends Task {

    private Action ifAction;
    private Action elseAction;

    public IfElseTask(Condition condition, Action ifAction, Action elseAction) {
        super(condition, ifAction);
        this.ifAction = ifAction;
        this.elseAction = elseAction;
    }

    @Override
    public int execute(Ui ui) {
        {
            if (checkCondition()) {
                return ifAction.execute(ui);
            } else {
                return elseAction.execute(ui);
            }
        }
    }
}