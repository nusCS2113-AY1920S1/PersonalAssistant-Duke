package UserCode.Conditions;

public abstract class Condition {

    public boolean check() {
        return false;
    }

    public static boolean validateCondition(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if (type.name().equals(userInput)) {
                return true;
            }
        }
        return false;
    }
}
