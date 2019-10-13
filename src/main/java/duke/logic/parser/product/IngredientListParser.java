//package duke.logic.parser.product;
//
//import duke.logic.parser.exceptions.ParseException;
//import duke.model.ingredient.IngredientList;
//
//import java.util.Dictionary;
//import java.util.Hashtable;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class IngredientListParser {
//    private String inputIngredientList;
//
//    //private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("((?:\\[)([a-zA-Z0-9_ ]*),"
//           // + "([a-zA-Z0-9_ ]*)(?:\\]))*");
//    private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("(?:\\[)([a-zA-Z0-9_ ]*),([a-zA-Z0-9_ ]*)(?:\\])");
//
//
//
//
//    /** Constructs a IngredientListParser with the userInput */
//    public IngredientListParser(String inputIngredientList) {
//        this.inputIngredientList = inputIngredientList;
//    }
//
//
//
//    public static Dictionary<String, String> getIngredients(String input) {
//        Matcher matcher = FORMAT_INGREDIENT_INPUT.matcher(input.trim());
//
//        if (!matcher.matches()) {
//            throw new ParseException("cannot match");
//        }
//
//
//        Dictionary<String, String> params = new Hashtable<>();
//
//        while (matcher.find()) {
//            String s = matcher.group().strip();
//            if (s.isEmpty() || s.isBlank()) continue;
//
//            if (matcher.group(1) != null) {
//                if (matcher.group(2) != null) {
//                    params.put(matcher.group(1), matcher.group(2));
//                } else {
//                    params.put(matcher.group(1), "");
//                }
//            }
//        }
//        System.out.println("params" + params);
//        return params;
//
//    }
//
//
//    public IngredientList getIngredientList() {
//        return new IngredientList() {};
//    }
//
//    //todo: add logic
//    public void addIngredients(String ingredientName) {
//        if (!IngredientList.contains(ingredientName)) {
//            //add ingredient;
//        }
//    }
//}
