package UserCode.Conditions;

import Farmio.Farmio;
import org.json.simple.JSONObject;

public class BooleanCondition extends Condition {

    public static final String JSON_KEY_TYPE = "condition_boolean_type";
    public static final String CONDITION_TYPE = "boolean";

    private BooleanConditionType type;

    public BooleanCondition (BooleanConditionType conditionType) {
        super(Type.BOOLEAN);
        this.type = conditionType;
    }

    @Override
    public boolean check(Farmio farmio) {
        return ConditionChecker.check(type, farmio);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = super.toJSON();
        object.put(JSON_KEY_TYPE, type.name());
        return object;
    }

    public String toString () {
        return type.name();
    }

}
