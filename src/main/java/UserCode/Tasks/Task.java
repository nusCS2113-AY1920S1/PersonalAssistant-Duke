package UserCode.Tasks;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import UserCode.Actions.Action;
import Exceptions.FarmioException;
import UserCode.Conditions.Condition;
import org.json.simple.JSONObject;

public abstract class Task {

    public enum Type {
        DO,
        IF,
        IF_ELSE,
        FOR,
        WHILE
    }

    public static final String JSON_KEY_CONDITION = "condition";
    public static final String JSON_KEY_ACTION = "action";
    public static final String JSON_KEY_TYPE = "type";

    protected Condition condition;
    protected Action action;

    private Type type;

    public Task(Type type, Condition condition, Action action) {
        this.condition = condition;
        this.action = action;
        this.type = type;
    }


    public boolean checkCondition(Farmio farmio) throws FarmioException {
        return condition.check(farmio);
    }

    public String toString() {
        return condition.toString() + " do " + action.toString();
    }


    public abstract void execute(Farmio farmio) throws FarmioException, FarmioFatalException;

    public static Task toTask(JSONObject object) throws FarmioException {
        Condition condition = Condition.toCondition((JSONObject) object.get(Task.JSON_KEY_CONDITION));
        Action action = Action.toAction((String) object.get(JSON_KEY_ACTION));
        switch(Type.valueOf((String) object.get(Task.JSON_KEY_TYPE))){
            case DO:
                return new DoTask(condition, action);
            case IF:
                return new IfTask(condition, action);
            case IF_ELSE:
                Action action_else = Action.toAction((String) object.get(IfElseTask.JSON_KEY_ACTION_ELSE));
                return new IfElseTask(condition, action, action_else);
            case FOR:
                return new ForTask(condition, action);
            case WHILE:
                return new WhileTask(condition, action);
            default:
                throw new FarmioException("Save is corrupted.");
        }
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        object.put(JSON_KEY_CONDITION, condition.toJSON());
        object.put(JSON_KEY_ACTION, action.toString());
        object.put(JSON_KEY_TYPE, type.name());
        return object;
    }

//    private Action parseJsonAction(JSONObject obj) throws FarmioException {
//        Action action;
//        switch ((String) obj.get("action")){
//            case "plant_seed":
//                action = new PlantSeedAction(obj);
//                break;
//            default:
//                throw new FarmioException("Invalid task action.");
//        }
//        return action;
//    }

//    public JSONObject toJSON(){
//        JSONObject obj = new JSONObject();
//        //obj.put("condition", condition.name());
//        obj.put("action", action.toJSON());
//        return obj;
//    }
}
