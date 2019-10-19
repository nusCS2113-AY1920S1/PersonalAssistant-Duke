package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;
import duke.model.order.Customer;
import duke.model.order.Order;
import duke.model.product.IngredientItemList;
import duke.model.product.Product;

import java.util.*;

/**
 * Jackson-friendly version of {@link duke.model.order.Order}.
 */
public class JsonAdaptedOrder {

    private final long id;

    private final Date creationDate;
    private final String name;
    private final String contact;
    private final String remarks;
    private final Date deliveryDate;
    private final Order.Status status;
    private final double total;
    private List<JsonAdaptedProductItem> items = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("id") long id,
                            @JsonProperty("creationDate")  Date creationDate,
                            @JsonProperty("name")  String name,
                            @JsonProperty("contact") String contact,
                            @JsonProperty("remarks")  String remarks,
                            @JsonProperty("deliveryDate") Date deliveryDate,
                            @JsonProperty("status") Order.Status status,
                            @JsonProperty("total") double total,
                            @JsonProperty("items") List<JsonAdaptedProductItem> items) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.contact = contact;
        this.remarks = remarks;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.total = total;
        this.items = items;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        this.id = source.getId();
        this.creationDate = source.getCreationDate();
        this.name = source.getCustomer().name;
        this.contact = source.getCustomer().contact;
        this.remarks = source.getRemarks();
        this.deliveryDate = source.getDeliveryDate();
        this.status = source.getStatus();
        this.total = source.getTotal();

        for (Item<Product> productItem : source.getItems()) {
            this.items.add(
                    new JsonAdaptedProductItem(
                            new JsonAdaptedProduct(productItem.getItem()),
                            productItem.getQuantity().getNumber()
                    )
            );
        }
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     */
    public Order toModelType() {
        Set<Item<Product>> productSet = new HashSet<>();
        for(JsonAdaptedProductItem jsonAdaptedProductItem : items) {
            productSet.add(jsonAdaptedProductItem.toModelType());

        }

        return new Order(
                new Customer(name, contact),
                deliveryDate,
                status,
                remarks,
                productSet,
                total,
                id,
                creationDate
        );
    }
}
