package UserCode.Conditions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public class ValueCondition extends Condition {
    ValueConditionType type;
    Comparator comparator;
    int value;

    public ValueCondition(ValueConditionType type, Comparator comparator, int value) {
        this.type = type;
        this.comparator = comparator;
        this.value = value;
    }

    @Override
    public boolean check(Farmio farmio) throws FarmioException {
        return ConditionChecker.check(type, comparator, value, farmio);
    }
}
