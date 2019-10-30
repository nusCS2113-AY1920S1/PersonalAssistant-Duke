package duke.logic.command.shopping;

import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClearShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "clear";

    private final List<Item<Ingredient>> emptyList;

    public ClearShoppingCommand() {
        emptyList = Collections.emptyList();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.clearShoppingList(emptyList);
        model.commit(ShoppingMessageUtils.MESSAGE_SUCCESS_CLEAR_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_CLEAR_SHOPPING),
                CommandResult.DisplayedPage.SHOPPING);
    }
}