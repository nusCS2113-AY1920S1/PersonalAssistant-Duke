package duke.logic.parser.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    //private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("((?:\\[)([a-zA-Z0-9_ ]*),"
           // + "([a-zA-Z0-9_ ]*)(?:\\]))*");




    /** Constructs a IngredientItemListParser with the userInput */
    public ItemParser(String inputIngredientList) {
        this.inputIngredientList = inputIngredientList;
    }

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
//    public IngredientItemList getIngredientList() {
//        return new IngredientItemList() {};
//    }
//
//    //todo: add logic
//    public void addIngredients(String ingredientName) {
//        if (!IngredientItemList.contains(ingredientName)) {
//            //add ingredient;
//        }
//    }
//}

}
