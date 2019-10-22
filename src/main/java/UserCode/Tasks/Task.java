package UserCode.Tasks;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import UserCode.Actions.Action;
import Exceptions.FarmioException;
import UserCode.Conditions.Condition;
import org.json.simple.JSONObject;

public abstract class Task {
    protected Condition condition;
    protected Action action;
    private String type;

    public Task(Condition condition, Action action, String type) {
        this.condition = condition;
        this.action = action;
        this.type = type;
    }
    /**
    public Task(JSONObject obj) throws FarmioException {
        this.condition = BooleanConditionType.valueOf((String) obj.get("condition"));
        this.action = parseJsonAction((JSONObject) obj.get("action"));
    }**/

    public boolean checkCondition(Farmio farmio) throws FarmioException {
        return condition.check(farmio);
    }

    public String toString() {
        return condition.toString() + " do " + action.toString();
    }


    public abstract void execute(Farmio farmio) throws FarmioException, FarmioFatalException;

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        object.put("condition", condition.toJSON());
        object.put("action", action.toString());
        object.put("type", type);
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
