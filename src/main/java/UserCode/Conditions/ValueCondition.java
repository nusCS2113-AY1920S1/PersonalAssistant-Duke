package UserCode.Conditions;

import Farmio.Farmio;
import Exceptions.FarmioException;
import org.json.simple.JSONObject;

public class ValueCondition extends Condition {
    private ValueConditionType type;
    private Comparator comparator;
    private int value;

    public ValueCondition(ValueConditionType type, Comparator comparator, int value) {
        this.type = type;
        this.comparator = comparator;
        this.value = value;
    }

    @Override
    public boolean check(Farmio farmio) throws FarmioException {
        return ConditionChecker.check(type, comparator, value, farmio);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("condition", "value");
        object.put("type", type.name());
        object.put("comparator", comparator.name());
        object.put("value", value);
        return object;
    }

    public String toString () {
        return type.name() + " " + comparatorToString() + " " + Integer.toString(value);
    }

    private String comparatorToString() {
        switch (comparator) {
            case greaterThanOrEquals:
                return ">=";
            case greaterThan:
                return ">";
            case lessThan:
                return "<";
            case lessThanOrEquals:
                return "<=";
        }
        return "";
    }
}
