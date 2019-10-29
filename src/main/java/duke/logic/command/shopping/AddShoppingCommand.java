package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import static java.util.Objects.requireNonNull;

public class AddShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "add";

    public static final String AUTO_COMPLETE_INDICATOR = ShoppingCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_SHOPPING_NAME,
        CliSyntax.PREFIX_SHOPPING_QUANTITY,
        CliSyntax.PREFIX_SHOPPING_COST,
        CliSyntax.PREFIX_SHOPPING_REMARKS
    };

    private final Item<Ingredient> toAdd;

    public AddShoppingCommand(Item<Ingredient> toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasShoppingList(toAdd)) {
            throw new CommandException(String.format(ShoppingMessageUtils.MESSAGE_DUPLICATE_SHOPPING,
                    toAdd.getItem().getName()));
        }

        model.addShoppingList(toAdd);
        model.commit(ShoppingMessageUtils.MESSAGE_COMMIT_ADD_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_ADD_SHOPPING,
                toAdd.getItem().getName()),
                CommandResult.DisplayedPage.SHOPPING);
    }
}