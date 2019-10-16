package duke.logic.command.shopping;

import duke.commons.core.Message;
import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class BuyShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "buy";
    public static final String MESSAGE_SUCCESS = "Shopping list ingredient bought";
    public static final String MESSAGE_INVALID_INDEX = "Please enter a valid index";

    private final Index index;

    public BuyShoppingCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item<Ingredient>> shoppingList = model.getFilteredShoppingList();
        List<Item<Ingredient>> inventoryList = model.getFilteredInventoryList();

        if (index.getZeroBased() >= shoppingList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Item<Ingredient> toBuy = shoppingList.get(index.getZeroBased());

        if (inventoryList.contains(toBuy)) {
            Double addedQuantity = toBuy.getQuantity().getNumber();
            Integer inventoryIndex = inventoryList.indexOf(toBuy);
            Double currentQuantity = inventoryList.get(inventoryIndex).getQuantity().getNumber();
            Double totalQuantity = currentQuantity + addedQuantity;

            Item<Ingredient> addedIngredient = ShoppingCommandUtil.createNewIngredient(toBuy, totalQuantity);
            model.setInventory(toBuy, addedIngredient);
        }
        else {
            model.addInventory(toBuy);
        }
        model.deleteShoppingList(toBuy);

        model.updateFilteredInventoryList(Model.PREDICATE_SHOW_ALL_INVENTORY);
        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_ALL_SHOPPING);

        return new CommandResult(String.format(MESSAGE_SUCCESS, CommandResult.DisplayedPage.INVENTORY));
    }
}
