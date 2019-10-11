package duke.logic.command.order;

import duke.logic.command.exceptions.CommandException;
import duke.model.commons.Item;
import duke.model.order.Order;
import duke.model.product.Product;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

class OrderCommandUtil {
    private static final String MESSAGE_ITEM_NOT_FOUND = "[%s] is not an existing product. "
            + "Add it to Product List first? ";

    static void checkProductExistence(ObservableList<Product> allProducts, Order order) throws CommandException {
        requireAllNonNull(allProducts, order);

        for (Item<Product> item : order.getItems()) {
            if (!allProducts.contains(item.getItem())) {
                throw new CommandException(String.format(MESSAGE_ITEM_NOT_FOUND, item.getItem().getName()));
            }
        }
    }

    static Set<Item<Product>> getProducts(List<Product> allProducts, Set<Item<String>> items) throws CommandException {
        requireAllNonNull(allProducts, items);

        Set<Item<Product>> products = new HashSet<>();
        for (Item<String> item : items) {
            if (!allProducts.contains(new Product(item.getItem()))) {
                throw new CommandException(String.format(MESSAGE_ITEM_NOT_FOUND, item.getItem()));
            } else {
                products.add(new Item<Product>(allProducts.get(allProducts.indexOf(new Product(item.getItem()))),
                        item.getQuantity()
                ));
            }
        }
        return products;
    }
}
