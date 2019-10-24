package duke.logic.command.shopping;

import duke.commons.core.Message;
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
    public static final String MESSAGE_SUCCESS = "Shopping list ingredient(s) bought. Total cost is: $%s";
    private static final Double ZERO_QUANTITY = 0.00;

    private final Set<Index> indices;
    private Double totalCost = 0.00;

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
                throw new CommandException(Message.MESSAGE_INVALID_INDEX);
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

            totalCost += toBuy.getTotalPrice();
            model.setShoppingList(toBuy, ShoppingCommandUtil.createNewIngredient(toBuy, ZERO_QUANTITY));
        }

        model.addSaleFromShopping(totalCost);

        return new CommandResult(String.format(MESSAGE_SUCCESS, totalCost, CommandResult.DisplayedPage.SHOPPING));
    }
}
