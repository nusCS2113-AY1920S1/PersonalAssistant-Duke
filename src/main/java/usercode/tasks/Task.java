package usercode.tasks;

import exceptions.FarmioFatalException;
import farmio.Farmio;
import usercode.actions.Action;
import exceptions.FarmioException;
import usercode.conditions.Condition;
import org.json.simple.JSONObject;

public abstract class Task {

    public enum Tasktype {
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

    private Tasktype type;

    /**
     * Creates a new Task object.
     *
     * @param type a tasktype enum
     * @param condition the Condition to check
     * @param action the Action to be executed
     */
    public Task(Tasktype type, Condition condition, Action action) {
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

    /**
     * Generates Task object from saved JSON data.
     *
     * @param object JSON Object to convert
     * @return Task object
     * @throws FarmioException if the JSON object cannot be converted successfully
     */
    public static Task toTask(JSONObject object) throws FarmioException {
        try {
            Condition condition = Condition.toCondition((JSONObject) object.get(Task.JSON_KEY_CONDITION));
            Action action = Action.toAction((String) object.get(JSON_KEY_ACTION));
            switch (Tasktype.valueOf((String) object.get(Task.JSON_KEY_TYPE))) {
            case DO:
                return new DoTask(condition, action);
            case IF:
                return new IfTask(condition, action);
            case IF_ELSE:
                Action actionElse = Action.toAction((String) object.get(IfElseTask.JSON_KEY_ACTION_ELSE));
                return new IfElseTask(condition, action, actionElse);
            case FOR:
                return new ForTask(condition, action);
            case WHILE:
                return new WhileTask(condition, action);
            default:
                throw new FarmioException("Game save is corrupted.");
            }
        } catch (Exception e) {
            throw new FarmioException("Game save is corrupted.");
        }
    }

    /**
     * Converts Task Object to JSON Object.
     *
     * @return the JSON Object to be stored in the save file
     */
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put(JSON_KEY_CONDITION, condition.toJson());
        object.put(JSON_KEY_ACTION, action.toString());
        object.put(JSON_KEY_TYPE, type.name());
        return object;
    }
}
