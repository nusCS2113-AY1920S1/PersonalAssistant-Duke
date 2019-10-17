package UserCode.Conditions;

import Farmio.Farmio;
import FarmioExceptions.FarmioException;

public abstract class Condition {

    public Condition() {}

    public boolean check(Farmio farmio) throws FarmioException {
        return false;
    }

    private static boolean validateBooleanCondition(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateValueCondition(String userInput) {
        if (userInput.matches("(\\w)+(\\s)*(((less)(\\s)*(than)(\\s)*(or)(\\s)*(equals))|(less(\\s)*than)|((greater)(\\s)*(than)(\\s)*(or)(\\s)*(equals))|((greater)(\\s)*(than)))(\\s)+(\\d)+")) {
            String value = userInput.substring(0, userInput.indexOf(" "));
            for (ValueConditionType c : ValueConditionType.values()) {
                if ((c.name()).toLowerCase().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean validateCondition(String userInput) {
        return (validateBooleanCondition(userInput) || validateValueCondition(userInput));
    }

    public static BooleanCondition toBooleanCondition (String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return new BooleanCondition(type);
            }
        }
        return new BooleanCondition(BooleanConditionType.TRUE);
    }

    public static ValueCondition toValueCondition (String userInput) {
        ValueConditionType valueConditionType = ValueConditionType.money;
        Comparator comparator = Comparator.lessThan;
        int value = 0;
        String conditionType = userInput.substring(0, userInput.indexOf(" "));
        for (ValueConditionType c : ValueConditionType.values()) {
            if ((c.name()).toLowerCase().equals(conditionType)) {
                valueConditionType = c;
            }
        }
        String comp = userInput.substring(userInput.indexOf(" "), userInput.lastIndexOf(" "));
        comp = comp.replaceAll("\\s+", "");
        for (Comparator c : Comparator.values()) {
            if ((c.name()).toLowerCase().equals(comp)) {
                comparator = c;
            }
        }
        String val = userInput.substring(userInput.lastIndexOf(" ") + 1);
        value = Integer.parseInt(val);
        return new ValueCondition(valueConditionType, comparator, value);
    }

    public static Condition toCondition(String userInput) throws FarmioException {
        if (validateBooleanCondition(userInput)) {
            return toBooleanCondition(userInput);
        } else if (validateValueCondition(userInput)) {
            return toValueCondition(userInput);
        }
        throw new FarmioException("Failure Creating Condition!");
    }
}
