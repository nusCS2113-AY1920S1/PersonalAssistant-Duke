package UserCode.Conditions;

public class BooleanCondition extends Condition {
    BooleanConditionType type;
    ConditionChecker checker;

    public BooleanCondition (BooleanConditionType conditionType, ConditionChecker conditionChecker) {
        this.type = conditionType;
        this.checker = conditionChecker;
    }

    @Override
    public boolean check() {
        return checker.check(type);
    }
}
