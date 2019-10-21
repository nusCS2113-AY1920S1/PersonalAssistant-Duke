package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import static java.util.Objects.requireNonNull;

public class AddShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_SUCCESS = "New ingredient added: %s";
    public static final String MESSAGE_DUPLICATE_SHOPPING = "%s already exists in the shopping list";

    private final Item<Ingredient> toAdd;

    public AddShoppingCommand(Item<Ingredient> toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasShoppingList(toAdd)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_SHOPPING, toAdd.getItem().getName()));
        }

        model.addShoppingList(toAdd);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getItem().getName()),
                CommandResult.DisplayedPage.SHOPPING);
    }
}