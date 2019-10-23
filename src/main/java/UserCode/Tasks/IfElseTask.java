package UserCode.Tasks;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Exceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import org.json.simple.JSONObject;

public class IfElseTask extends Task {

    public static final String JSON_KEY_ACTION_ELSE = "action_else";

    private Action ifAction;
    private Action elseAction;

    public IfElseTask(Condition condition, Action ifAction, Action elseAction) {
        super(Type.IF_ELSE, condition, ifAction);
        this.ifAction = ifAction;
        this.elseAction = elseAction;
    }

    @Override
    public void execute(Farmio farmio) throws FarmioException, FarmioFatalException {
        {
            if (checkCondition(farmio)) {
                ifAction.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
            } else {
                elseAction.execute(farmio.getUi(), farmio.getStorage(), farmio.getFarmer(), farmio.getSimulation());
            }
        }
    }

    @Override
    public String toString() {
        return "if " + condition.toString() + " " + ifAction.toString() + " else " + elseAction.toString();
    }

    @Override
    public JSONObject toJSON(){
        JSONObject object = super.toJSON();
        object.put(JSON_KEY_ACTION_ELSE, elseAction.toString());
        return object;
    }
}