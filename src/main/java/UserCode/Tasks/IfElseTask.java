package UserCode.Tasks;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;
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
    public void execute(Farmio farmio) throws FarmioException {
        {
            if (checkCondition(farmio)) {
                ifAction.execute(farmio.getUi());
            } else {
                elseAction.execute(farmio.getUi());
            }
        }
    }
}