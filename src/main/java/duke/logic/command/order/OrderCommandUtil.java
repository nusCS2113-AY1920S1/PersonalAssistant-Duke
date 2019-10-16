package duke.logic.command.order;

import duke.logic.command.exceptions.CommandException;
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

    static Set<Item<Product>> getProducts(List<Product> allProducts, Set<Item<String>> items) throws CommandException {
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

    static Order createNewOrder(Order original, OrderDescriptor orderDescriptor,
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
            newItems = getProducts(productList, orderDescriptor.getItems().get());
        } else {
            newItems = original.getItems();
        }

        String newRemarks = orderDescriptor.getRemarks().orElse(original.getRemarks());
        Order.Status newStatus = orderDescriptor.getStatus().orElse(original.getStatus());
        double newTotal = orderDescriptor.getTotal().orElse(original.getTotal());
        return new Order(newCustomer, newDate, newStatus, newRemarks, newItems, newTotal, inventoryList);
    }

    static double calculateTotal(Set<Item<Product>> productItems) {
        double total = 0;
        for (Item<Product> productItem : productItems) {
            total += productItem.getItem().getRetailPrice() * productItem.getQuantity().getNumber();
        }
        return total;
    }
}
