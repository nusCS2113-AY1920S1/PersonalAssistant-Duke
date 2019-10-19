package UserCode.Tasks;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

public class IfElseTask extends Task {

    private Action ifAction;
    private Action elseAction;

    public IfElseTask(Condition condition, Action ifAction, Action elseAction) {
        super(condition, ifAction);
        this.ifAction = ifAction;
        this.elseAction = elseAction;
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        {
            if (checkCondition(farmio)) {
                ifAction.execute(farmio.getUi());
            } else {
                elseAction.execute(farmio.getUi());
            }
        }
    }

    @Override
    public String toString() {
        return "if " + condition.toString() + " " + ifAction.toString() + " else " + elseAction.toString();
    }
}