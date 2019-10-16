package UserCode.Conditions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

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
