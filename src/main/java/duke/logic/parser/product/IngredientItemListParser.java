package duke.logic.parser.product;

import duke.logic.parser.exceptions.ParseException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.commons.Quantity;
import duke.model.product.IngredientItemList;

import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientItemListParser {
    private String inputIngredientList;

    //private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("((?:\\[)([a-zA-Z0-9_ ]*),"
           // + "([a-zA-Z0-9_ ]*)(?:\\]))*");
    private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("((\\s*\\[)(?<name>[\\w ]+)([,]?)"
            + "(?<quantity>[0-9. ]*)(?:\\]\\s*))+");

    private static final String MESSAGE_PORTION_NOT_NUMBER = "Ingredient portion must be a number";

    /** Constructs a IngredientItemListParser with the userInput */
    public IngredientItemListParser(String inputIngredientList) {
        this.inputIngredientList = inputIngredientList;
    }

    private static Map<String, String> getIngredientPortion(String input) {
        String replacement = input;
        Matcher matcher = FORMAT_INGREDIENT_INPUT.matcher(input.trim());

        if (!matcher.matches()) {
            throw new ParseException("Wrong ingredient Format");
        }

        Map<String, String> params = new Hashtable<>();

        while (matcher.matches()) {
            String s = matcher.group().strip();
            if (s.isEmpty() || s.isBlank()) {
                continue;
            }
            if (matcher.group("name") != null) {
                if (matcher.group("quantity") != null) {
                    params.put(matcher.group("name"), matcher.group("quantity"));
                } else {
                    params.put(matcher.group("name"), "");
                }
            }
            replacement = replacement.replaceAll("(\\s*\\[)(?<name>[\\w ]+)([,]?)"
                    + "(?<quantity>[0-9. ]*)(?:\\]\\s*)$", "");
            matcher = FORMAT_INGREDIENT_INPUT.matcher(replacement);
        }
        return params;
    }

    private static Item<Ingredient> constructNewIngredientItem(Map.Entry<String, String> entry) {
        String ingredientName = entry.getKey();
        String portionString = entry.getValue();
        Ingredient newIngredient = new Ingredient(ingredientName);
        Double portion;
        try {
            portion = Double.parseDouble(portionString);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_PORTION_NOT_NUMBER);
        }
        Quantity quantity = new Quantity(portion);
        return new Item<Ingredient>(newIngredient, quantity);
    }


    //private static Item<Ingredient> constructIngredientItem(Map.Entry<String, String> entry) {
    //    //Todo: Implement search
    //    if (false) {
    //        return null;
    //    }
    //    return constructNewIngredientItem(entry);
    //}

    public static IngredientItemList getIngredientsInInput(String input) {
        IngredientItemList ingredientItemList = new IngredientItemList();
        Map<String, String> ingredientAndPortion = getIngredientPortion(input);
        for (Map.Entry<String, String> entry : ingredientAndPortion.entrySet()) {
            Item<Ingredient> ingredientItem = constructNewIngredientItem(entry);
            ingredientItemList.add(ingredientItem);
        }
        return ingredientItemList;
    }
}
