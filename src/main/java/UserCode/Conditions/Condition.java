package UserCode.Conditions;

public abstract class Condition {

    public Condition() {}

    public boolean check() {
        return false;
    }

    public static boolean validateBooleanCondition(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateMoneyCondition(String userInput) {
        if (userInput.matches("(money)(\\s)*(((less)(\\s)*(than)(\\s)*(or)(\\s)*(equals))|(less(\\s)*than)|((greater)(\\s)*(than)(\\s)*(or)(\\s)*(equals))|((greater)(\\s)*(than)))(\\s)+(\\d)+")) {
            return true;
        }
        return false;
    }

    public static BooleanConditionType toBooleanCondition (String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return type;
            }
        }
        return BooleanConditionType.TRUE;
    }
}
