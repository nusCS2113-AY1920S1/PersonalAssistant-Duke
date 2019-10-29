package usercode.conditions;

import farmio.Farmio;
import exceptions.FarmioException;
import org.json.simple.JSONObject;

public abstract class Condition {

    public enum Type {
        BOOLEAN,
        VALUE
    }

    public static final String JSON_KEY_TYPE = "condition_type";

    private Type type;

    public Condition(Type type) {
        this.type = type;
    }

    public Condition(JSONObject object){
    }

    public boolean check(Farmio farmio) throws FarmioException {
        return false;
    }

    /**
     * Checks if the user input condition is a valid boolean condition.
     *
     * @param userInput substring of the user input String that contains the condition
     * @return true if the String is a valid BooleanCondition, false otherwise
     */
    private static boolean isValidBooleanCondition(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the user input condition is a valid value condition.
     * A value condition is a condition that consists of a valid asset, a comparator,
     * and an integer. The function uses regex to check if the String is of the correct format,
     * then checks if the asset name, comparator, and integer value is valid.
     *
     * @param userInput substring of the user input String that contains the condition
     * @return true if the String is a valid ValueCondition, false otherwise
     */
    private static boolean isValidValueCondition(String userInput) {
        if (userInput.matches("(\\w)+(\\s)*(((less)(\\s)*(than)(\\s)*(or)(\\s)*(equals))|(less(\\s)*than)|"
                + "((greater)(\\s)*(than)(\\s)*(or)(\\s)*(equals))|((greater)(\\s)*(than)))(\\s)+(\\d)+")) {
            String value = userInput.substring(0, userInput.indexOf(" "));
            for (ValueConditionType c : ValueConditionType.values()) {
                if ((c.name()).toLowerCase().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the user input condition is a valid condition, by checking whether it is a valid
     * BooleanCondition or valid ValueCondition.
     *
     * @param userInput substring of the user input String that contains the condition
     * @return true if the String is a valid Condition, false otherwise
     */
    public static boolean isValidCondition(String userInput) {
        return (isValidBooleanCondition(userInput) || isValidValueCondition(userInput));
    }

    /**
     * Converts String into a BooleanCondition object. The String is assumed to be a valid BooleanCondition.
     * If not, the TRUE condition is returned.
     *
     * @param userInput String with the name of the condition
     * @return BooleanCondition object named by the String
     */
    private static BooleanCondition toBooleanCondition(String userInput) {
        for (BooleanConditionType type : BooleanConditionType.values()) {
            if ((type.name()).toLowerCase().equals(userInput)) {
                return new BooleanCondition(type);
            }
        }
        return new BooleanCondition(BooleanConditionType.TRUE);
    }

    /**
     * Converts String into a ValueCondition object. The String is assumed to be a valid ValueCondition.
     *
     * @param userInput String that contains description for the ValueCondition
     * @return ValueCondition object described from the String
     */
    private static ValueCondition toValueCondition(String userInput) {
        ValueConditionType valueConditionType = ValueConditionType.gold;
        Comparator comparator = Comparator.lessThan;
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
        int value = 0;
        String val = userInput.substring(userInput.lastIndexOf(" ") + 1);
        value = Integer.parseInt(val);
        return new ValueCondition(valueConditionType, comparator, value);
    }

    /**
     * Converts String into a Condition object. If the String represents a BooleanCondition, uses the
     * toBooleanCondition function to create the object. If the String represents a ValueCondition,
     * uses the toValueCondition function to create the object.
     *
     * @param userInput String that contains the description of the Condition
     * @return Condition object described by the String
     * @throws FarmioException if the String is not a valid BooleanCondition or ValueCondition
     */
    public static Condition toCondition(String userInput) throws FarmioException {
        if (isValidBooleanCondition(userInput)) {
            return toBooleanCondition(userInput);
        } else if (isValidValueCondition(userInput)) {
            return toValueCondition(userInput);
        }
        throw new FarmioException("Failure Creating Condition!");
    }

    /**
     * Converts a JSONObject to a Condition object.
     *
     * @param object JSONObject read from the save file
     * @return Condition object created from the JSONObject
     * @throws FarmioException if the JSONObject fails to create a Condition object
     */
    public static Condition toCondition(JSONObject object) throws FarmioException {
        String conditionType = (String) object.get(JSON_KEY_TYPE);
        switch (Type.valueOf((String) object.get(JSON_KEY_TYPE))) {
        case BOOLEAN:
            BooleanConditionType booleanConditionType = BooleanConditionType.valueOf((String)
                    object.get(BooleanCondition.JSON_KEY_TYPE));
            return new BooleanCondition(booleanConditionType);
        case VALUE:
            ValueConditionType valueConditionType  = ValueConditionType.valueOf((String)
                    object.get(ValueCondition.JSON_KEY_TYPE));
            Comparator comparator = Comparator.valueOf((String) object.get(ValueCondition.JSON_KEY_COMPARATOR));
            int value = (int) (long) object.get(ValueCondition.JSON_KEY_VALUE);
            return new ValueCondition(valueConditionType, comparator, value);
        default:
            throw new FarmioException("Gave Save Corrupted!");
        }
    }

    /**
     * Converts the Condition object to a JSONObject.
     *
     * @return
     */
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put(JSON_KEY_TYPE, type.name());
        return object;
    }
}
