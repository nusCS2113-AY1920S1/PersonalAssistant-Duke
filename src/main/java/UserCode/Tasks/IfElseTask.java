package UserCode.Tasks;

import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import FrontEnd.Ui;

public class IfElseTask extends Task {

    private Action ifAction;
    private Action elseAction;

    public IfElseTask(Condition condition, Action ifAction, Action elseAction) {
        super(condition, ifAction);
        this.ifAction = ifAction;
        this.elseAction = elseAction;
    }

    @Override
    public void execute(Ui ui) {
        {
            if (checkCondition()) {
                ifAction.execute(ui);
            } else {
                elseAction.execute(ui);
            }
        }
    }
}