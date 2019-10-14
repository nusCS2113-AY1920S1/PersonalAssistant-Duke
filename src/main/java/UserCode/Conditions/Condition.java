package UserCode.Conditions;

public abstract class Condition {

    public Condition() {}

    public boolean check() {
        return false;
    }

    public static boolean validateBooleanCondition(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if (type.name().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateMoneyCondition(String userInput) {
        if (userInput.matches("(money)\\s(>|>=|<|<=)\\s(\\d)+")) {
            return true;
        }
        return false;
    }

    public static BooleanConditionType getConditionTypeFromString(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if (type.name().equals(userInput)) {
                return type;
            }
        }
        return BooleanConditionType.TRUE;
    }
}
