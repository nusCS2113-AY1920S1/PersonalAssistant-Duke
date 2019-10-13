package UserCode.Conditions;

import Places.Market;

public class MoneyCondition extends Condition{
    int value;
    boolean isGreater;
    ConditionChecker conditionChecker;

    public MoneyCondition(boolean greater, int val, ConditionChecker conditionChecker) {
        this.isGreater = greater;
        this.value = val;
        this.conditionChecker = conditionChecker;
    }

    @Override
    public boolean check() {
        return conditionChecker.check(isGreater, value);
    }
}
