package duke.logic.parser.product;

import duke.logic.command.product.AddProductCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.ingredient.IngredientList;
import duke.model.product.Product;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static duke.logic.parser.commons.CliSyntax.PREFIX_CUSTOMER_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_INGREDIENT;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_PRODUCT_RETAIL_PRICE;


public class AddProductCommandParser implements Parser<AddProductCommand> {
    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AddProductCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_PRODUCT_NAME,
                PREFIX_PRODUCT_INGREDIENT,
                PREFIX_PRODUCT_INGREDIENT_COST,
                PREFIX_PRODUCT_RETAIL_PRICE
        );

        IngredientList ingredients = new IngredientList(){};


        Product product = new Product(
                map.getValue(PREFIX_CUSTOMER_NAME).orElse("ProductName"),
                map.getValue(PREFIX_PRODUCT_RETAIL_PRICE).orElse(String.valueOf(0)),
                map.getValue(PREFIX_PRODUCT_INGREDIENT_COST).orElse(String.valueOf(0))
        );

        System.out.println(map.getValue(PREFIX_PRODUCT_INGREDIENT));
        ingredientListParser(map.getValue(PREFIX_PRODUCT_INGREDIENT).orElse(""));
        return new AddProductCommand(product);
    }

    //Todo: IngredientList Parser -ingt [ingredient_name, qty] [ingredient_name2, qty] [ingredient_name3]
    private void ingredientListParser(String input) {
        System.out.println("input" + input);
        Pattern pattern = Pattern.compile("((\\[)(?<name>[\\w|\\s]*),(?<description>[\\w|\\s]*)(?:\\])"
                + "\\s*)+");
        Matcher matcher = pattern.matcher(input.trim());

        if (!matcher.matches()) {
            System.out.println("no"
                    + "Match");
         //   throw new ParseException("no match");
        }


        Dictionary<String, String> params = new Hashtable<>();

        while (matcher.matches()) {
            String s = matcher.group().strip();
            if (s.isEmpty() || s.isBlank()) continue;
            if (matcher.group("name") != null) {
                System.out.println(matcher.group("name"));
                System.out.println(matcher.group("description"));
                if (matcher.group("description") != null) {
                    params.put(matcher.group("name"), matcher.group("description"));
                } else {
                    params.put(matcher.group("name"), "");
                }
            }
            input = input.replaceAll("\\s*((\\[)(?<name>[\\w|\\s]*),(?<description>[\\w|\\s]*)(?:\\])"
                    + "\\s*)$", "");
            matcher = pattern.matcher(input);
        }
      //  System.out.println("params" + params);

    }
}
