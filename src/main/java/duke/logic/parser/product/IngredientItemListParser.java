package duke.logic.parser.product;

import duke.commons.core.LogsCenter;
import duke.logic.message.ProductMessageUtils;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.StringUtils;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientItemListParser {

    private static final Logger logger = LogsCenter.getLogger(ProductParserUtil.class);

    private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("((\\s*\\[\\s*)(?<name>[\\w ]+)"
        + "([,"
        + "]?)"
            + "(?<quantity>[0-9. ]*)(?:\\]\\s*))+");
    private static final Double DEFAULT_PORTION = 0.0;

    private static Map<String, String> getIngredientPortionMap(String input) {
        String replacement = input;
        Matcher matcher = FORMAT_INGREDIENT_INPUT.matcher(input.trim());

        if (!matcher.matches()) {
            throw new ParseException("Wrong ingredient format");
        }

        Map<String, String> params = new Hashtable<>();

        while (matcher.matches()) {
            String s = matcher.group().strip();
            if (s.isEmpty() || s.isBlank()) {
                continue;
            }
            if (matcher.group("name") != null) {
                if (matcher.group("quantity") != null) {
                    String s1 = matcher.group("name").strip();
                    String name = StringUtils.capitalize(s1.toLowerCase());
                    params.put(name, matcher.group("quantity"));
                } else {
                    params.put(matcher.group("name"), "");
                }
            }
            replacement = replacement.replaceAll("(\\s*\\[\\s*)(?<name>[\\w ]+)([,]?)"
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
        if (portionString.isBlank()) {
            portion = DEFAULT_PORTION;
        } else {
            try {
                portion = Double.parseDouble(portionString);
            } catch (NumberFormatException e) {
                logger.info(ProductMessageUtils.MESSAGE_PORTION_NOT_NUMBER);
                throw new ParseException(ProductMessageUtils.MESSAGE_PORTION_NOT_NUMBER);
            }
        }

        Quantity quantity = new Quantity(portion);
        return new Item<Ingredient>(newIngredient, quantity);
    }

    /**
     * Generates a list of Ingredient Items from user input.
     *
     * @param input user input containing ingredient and portion
     * @return the IngredientItemList that contains the ingredients
     */
    public static IngredientItemList getIngredientsInInput(String input) {
        IngredientItemList ingredientItemList = new IngredientItemList();
        Map<String, String> ingredientAndPortion = getIngredientPortionMap(input);
        for (Map.Entry<String, String> entry : ingredientAndPortion.entrySet()) {
            Item<Ingredient> ingredientItem = constructNewIngredientItem(entry);
            ingredientItemList.add(ingredientItem);
        }
        return ingredientItemList;
    }
}
