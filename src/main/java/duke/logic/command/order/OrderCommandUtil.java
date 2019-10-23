package duke.logic.command.order;

import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.order.Customer;
import duke.model.order.Order;
import duke.model.product.Product;
import javafx.collections.ObservableList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

class OrderCommandUtil {
    private static final String MESSAGE_ITEM_NOT_FOUND = "[%s] is not an existing product. "
            + "Add it to Product List first? ";

    /**
     * Returns a set of product items from product names.
     * @param allProducts that are available.
     * @param items containing the name of the products.
     * @throws CommandException if one or more product names in {@code items} are not found in {@code allProducts}
     */
    static Set<Item<Product>> getProductItems(List<Product> allProducts,
                                              Set<Item<String>> items)
            throws CommandException {
        requireAllNonNull(allProducts, items);

        Set<Item<Product>> products = new HashSet<>();
        for (Item<String> item : items) {
            if (!allProducts.contains(new Product(item.getItem()))) {
                throw new CommandException(String.format(MESSAGE_ITEM_NOT_FOUND, item.getItem()));
            } else {
                products.add(new Item<>(allProducts.get(allProducts.indexOf(new Product(item.getItem()))),
                        item.getQuantity()
                ));
            }
        }
        return products;
    }

    /**
     * Modifies the {@code original} order based on {@code orderDescriptor}.
     * {@code inventoryList} is used to detect if there are enough ingredients for the order.
     * @throws CommandException if if items specified in the descriptor are not in {@code productList}.
     */
    static Order modifyOrder(Order original, OrderDescriptor orderDescriptor,
                             List<Product> productList,
                             ObservableList<Item<Ingredient>> inventoryList)
        throws CommandException {

        assert original != null;

        Customer newCustomer = new Customer(
                orderDescriptor.getCustomerName().orElse(original.getCustomer().name),
                orderDescriptor.getCustomerContact().orElse(original.getCustomer().contact)
        );

        Date newDate = orderDescriptor.getDeliveryDate().orElse(original.getDeliveryDate());

        Set<Item<Product>> newItems;
        if (orderDescriptor.getItems().isPresent()) {
            newItems = getProductItems(productList, orderDescriptor.getItems().get());
        } else {
            newItems = original.getItems();
        }

        String newRemarks = orderDescriptor.getRemarks().orElse(original.getRemarks());
        Order.Status newStatus = orderDescriptor.getStatus().orElse(original.getStatus());
        double newTotal = orderDescriptor.getTotal().orElse(original.getTotal());
        Order order = new Order(newCustomer, newDate, newStatus, newRemarks, newItems, newTotal);
        order.listenToInventory(inventoryList);
        return order;
    }

    /**
     * Deducts the amount of ingredients used in this {@code order} from inventory in {@code model}.
     * If ingredients in inventory are not enough, deducts to zero.
     *
     * @return true if ingredients in inventory are enough.
     */
    static boolean deductInventory(Order order, Model model) {
        boolean isInventoryEnough = true;
        for (Item<Product> productItem : order.getItems()) {
            for (Item<Ingredient> ingredientItem : productItem.getItem().getIngredients()) {
                if (model.hasIngredient(ingredientItem.getItem())) {
                    if (model.deductIngredient(ingredientItem.getItem(),
                        productItem.getQuantity().getNumber() * ingredientItem.getQuantity().getNumber()
                    )) {
                        isInventoryEnough = false;
                    }
                }
            }
        }
        return isInventoryEnough;
    }
}
