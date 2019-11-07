package duke.logic.command.shopping;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.logic.message.ShoppingMessageUtils;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class BuyShoppingCommand extends ShoppingCommand {

    public static final String COMMAND_WORD = "buy";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "No ingredient found at index [%d].";

    private static final Double ZERO_QUANTITY = 0.00;
    private final Set<Index> indices;
    private Double totalCost = 0.00;
    private ArrayList<Item<Ingredient>> toBuyList;

    public BuyShoppingCommand(Set<Index> indices) {
        requireNonNull(indices);
        this.indices = indices;
        toBuyList = new ArrayList<>();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Item<Ingredient>> shoppingList = model.getFilteredShoppingList();
        List<Item<Ingredient>> inventoryList = model.getFilteredInventoryList();

        for (Index index : indices) {
            if (index.getZeroBased() >= shoppingList.size()) {
                throw new CommandException(String.format(MESSAGE_INGREDIENT_NOT_FOUND, index.getOneBased()));
            }
        }

        for (Index index : indices) {
            Item<Ingredient> toBuy = shoppingList.get(index.getZeroBased());
            toBuyList.add(toBuy);

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

            model.setShoppingList(toBuy, ShoppingCommandUtil.createNewIngredient(toBuy, ZERO_QUANTITY));
        }

        totalCost = model.computeTotalCost(toBuyList);
        model.addSaleFromShopping(totalCost, toBuyList);
        model.commit(ShoppingMessageUtils.MESSAGE_COMMIT_BUY_SHOPPING);

        return new CommandResult(String.format(ShoppingMessageUtils.MESSAGE_SUCCESS_BUY_SHOPPING,
                totalCost, CommandResult.DisplayedPage.SHOPPING));
    }
}
