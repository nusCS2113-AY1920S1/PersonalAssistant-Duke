package duke.logic.parser.shopping;

import duke.logic.command.shopping.AddShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.StringUtils;

import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_COST;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_NAME;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_QUANTITY;
import static duke.logic.parser.commons.CliSyntax.PREFIX_SHOPPING_REMARKS;

public class AddShoppingCommandParser implements Parser<AddShoppingCommand> {

    private static final String DEFAULT_COST = "0.00";
    private static final String DEFAULT_QUANTITY = "0.0";
    private static final String EMPTY_STRING = "";

    @Override
    public AddShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SHOPPING_NAME,
                PREFIX_SHOPPING_COST,
                PREFIX_SHOPPING_QUANTITY,
                PREFIX_SHOPPING_REMARKS
        );

        Ingredient ingredient = new Ingredient(
                StringUtils.capitalize(map.getValue(PREFIX_SHOPPING_NAME).orElse(EMPTY_STRING).toLowerCase()),
                Double.parseDouble(map.getValue(PREFIX_SHOPPING_COST).orElse(DEFAULT_COST)),
                map.getValue(PREFIX_SHOPPING_REMARKS).orElse(EMPTY_STRING)
        );

        Quantity quantity = new Quantity(
                Double.parseDouble(map.getValue(PREFIX_SHOPPING_QUANTITY).orElse(DEFAULT_QUANTITY))
        );

        Item<Ingredient> shoppingItem = new Item<Ingredient>(ingredient, quantity);

        return new AddShoppingCommand(shoppingItem);
    }
}
