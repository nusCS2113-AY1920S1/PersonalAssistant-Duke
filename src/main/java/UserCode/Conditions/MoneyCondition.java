package UserCode.Conditions;

import Places.Market;

public class MoneyCondition extends Condition{
    int value;
    Comparator comparator;
    ConditionChecker conditionChecker;

    public MoneyCondition(Comparator comparator, int val, ConditionChecker conditionChecker) {
        this.comparator = comparator;
        this.value = val;
        this.conditionChecker = conditionChecker;
    }

    @Override
    public boolean check() {
        return conditionChecker.check(comparator, value);
    }
}
