package UserCode.Conditions;

import Farmio.Farmio;

public class BooleanCondition extends Condition {
    BooleanConditionType type;

    public BooleanCondition (BooleanConditionType conditionType) {
        this.type = conditionType;
    }

    @Override
    public boolean check(Farmio farmio) {
        return ConditionChecker.check(type, farmio);
    }
}
