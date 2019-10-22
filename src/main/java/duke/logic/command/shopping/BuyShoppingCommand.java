package duke.logic.command.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class BuyShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "buy";
    public static final String MESSAGE_SUCCESS = "Shopping list ingredient(s) bought";
    public static final String MESSAGE_INVALID_INDEX = "Please enter a valid index";
    private static final Double EMPTY = 0.0;


    private final Set<Index> indices;

    public BuyShoppingCommand(Set<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item<Ingredient>> shoppingList = model.getFilteredShoppingList();
        List<Item<Ingredient>> inventoryList = model.getFilteredInventoryList();

        for (Index index : indices) {
            if (index.getZeroBased() >= shoppingList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
        }

        for (Index index : indices) {
            Item<Ingredient> toBuy = shoppingList.get(index.getZeroBased());

            if (inventoryList.contains(toBuy)) {
                Double addedQuantity = toBuy.getQuantity().getNumber();
                Integer inventoryIndex = inventoryList.indexOf(toBuy);
                Double currentQuantity = inventoryList.get(inventoryIndex).getQuantity().getNumber();
                Double totalQuantity = currentQuantity + addedQuantity;

                Item<Ingredient> addedIngredient = ShoppingCommandUtil.createNewIngredient(toBuy, totalQuantity);
                model.setInventory(toBuy, addedIngredient);
            } else {
                model.addInventory(toBuy);
            }

            model.setShoppingList(toBuy, ShoppingCommandUtil.createNewIngredient(toBuy, EMPTY));
        }

        model.updateFilteredInventoryList(Model.PREDICATE_SHOW_ALL_INVENTORY);
        model.updateFilteredShoppingList(Model.PREDICATE_SHOW_ALL_SHOPPING);

        return new CommandResult(String.format(MESSAGE_SUCCESS, CommandResult.DisplayedPage.SHOPPING));
    }
}
