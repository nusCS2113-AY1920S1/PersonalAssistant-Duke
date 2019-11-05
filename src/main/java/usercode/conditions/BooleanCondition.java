package usercode.conditions;

import farmio.Farmio;
import org.json.simple.JSONObject;

public class BooleanCondition extends Condition {

    public static final String JSON_KEY_TYPE = "condition_boolean_type";

    private BooleanConditionType type;

    public BooleanCondition(BooleanConditionType conditionType) {
        super(Type.BOOLEAN);
        this.type = conditionType;
    }

    @Override
    public boolean check(Farmio farmio) {
        return ConditionChecker.check(type, farmio);
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = super.toJson();
        object.put(JSON_KEY_TYPE, type.name());
        return object;
    }

    public String toString() {
        return type.name();
    }

}
