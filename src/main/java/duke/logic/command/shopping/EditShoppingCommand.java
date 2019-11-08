package duke.logic.command.shopping;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.logic.parser.commons.CliSyntax;
import duke.logic.parser.commons.Prefix;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.exceptions.DuplicateEntityException;
import duke.model.inventory.Ingredient;

import java.util.List;

import static duke.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

public class EditShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "edit";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "No ingredient found at index [%d].";

    public static final String AUTO_COMPLETE_INDICATOR = ShoppingCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final Prefix[] AUTO_COMPLETE_PARAMETERS = {
        CliSyntax.PREFIX_SHOPPING_NAME,
        CliSyntax.PREFIX_SHOPPING_QUANTITY,
        CliSyntax.PREFIX_SHOPPING_COST,
        CliSyntax.PREFIX_SHOPPING_REMARKS
    };

    public final Index index;
    public final ShoppingDescriptor shoppingDescriptor;

    public EditShoppingCommand(Index index, ShoppingDescriptor edited) {
        requireAllNonNull(index, edited);

        this.index = index;
        shoppingDescriptor = edited;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item<Ingredient>> lastShownList = model.getFilteredShoppingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, index.getOneBased()));
        }

        Item<Ingredient> toEdit = lastShownList.get(index.getZeroBased());

        Item<Ingredient> edited = ShoppingCommandUtil.createNewIngredient(toEdit, shoppingDescriptor);
        try {
            model.setShoppingList(toEdit, edited);
        } catch (DuplicateEntityException e) {
            throw new CommandException(String.format(ShoppingMessageUtils.MESSAGE_DUPLICATE_SHOPPING,
                    edited.getItem().getName()));
        }
        model.setShoppingList(toEdit, edited);
        model.commit(ShoppingMessageUtils.MESSAGE_COMMIT_EDIT_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_EDIT_SHOPPING,
                edited.getItem().getName()),
                CommandResult.DisplayedPage.SHOPPING);
    }
}