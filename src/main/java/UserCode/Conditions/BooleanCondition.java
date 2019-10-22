package UserCode.Conditions;

import Farmio.Farmio;
import org.json.simple.JSONObject;

public class BooleanCondition extends Condition {
    private BooleanConditionType type;

    public BooleanCondition (BooleanConditionType conditionType) {
        this.type = conditionType;
    }

    @Override
    public boolean check(Farmio farmio) {
        return ConditionChecker.check(type, farmio);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("condition", "boolean");
        object.put("type", type.name());
        return object;
    }

    public String toString () {
        return type.name();
    }

}
