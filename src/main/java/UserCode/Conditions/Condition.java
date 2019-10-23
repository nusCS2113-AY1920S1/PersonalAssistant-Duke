package UserCode.Conditions;

import Farmio.Farmio;
import Exceptions.FarmioException;
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

    public static Condition toCondition(JSONObject object) throws FarmioException {
        String conditionType = (String) object.get(JSON_KEY_TYPE);
        switch(Type.valueOf((String) object.get(JSON_KEY_TYPE))){
            case BOOLEAN:
                BooleanConditionType booleanConditionType = BooleanConditionType.valueOf((String) object.get(BooleanCondition.JSON_KEY_TYPE));
                return new BooleanCondition(booleanConditionType);
            case VALUE:
                ValueConditionType valueConditionType  = ValueConditionType.valueOf((String) object.get(ValueCondition.JSON_KEY_TYPE));
                Comparator comparator = Comparator.valueOf((String) object.get(ValueCondition.JSON_KEY_COMPARATOR));
                int value = (int) (long) object.get(ValueCondition.JSON_KEY_VALUE);
                return new ValueCondition(valueConditionType, comparator, value);
            default:
                throw new FarmioException("Gave Save Corrupted!");
        }
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        object.put(JSON_KEY_TYPE, type.name());
        return object;
    }
}
