package duke.logic.parser.shopping;

import duke.logic.command.shopping.AddShoppingCommand;
import duke.logic.parser.commons.ArgumentMultimap;
import duke.logic.parser.commons.ArgumentTokenizer;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Quantity;

import static duke.logic.parser.commons.CliSyntax.*;

public class AddShoppingCommandParser implements Parser<AddShoppingCommand> {

    @Override
    public AddShoppingCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                PREFIX_SHOPPING_NAME,
                PREFIX_SHOPPING_COST,
                PREFIX_SHOPPING_QUANTITY,
                PREFIX_SHOPPING_REMARKS
        );

        Ingredient ingredient = new Ingredient(
                map.getValue(PREFIX_SHOPPING_NAME).orElse(""),
                Double.parseDouble(map.getValue(PREFIX_SHOPPING_COST).orElse(String.valueOf(0))),
                map.getValue(PREFIX_SHOPPING_REMARKS).orElse("")
        );

        Quantity quantity = new Quantity(
                Integer.parseInt(map.getValue(PREFIX_SHOPPING_QUANTITY).orElse(String.valueOf(0)))
        );

        Item<Ingredient> shoppingItem = new Item<Ingredient>(ingredient, quantity);

        return new AddShoppingCommand(shoppingItem);
    }
}
