package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import duke.logic.command.exceptions.IllegalValueException;
import duke.model.BakingHome;
import duke.model.ReadOnlyBakingHome;
import duke.model.order.Order;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "bakinghome")
class JsonSerializableBakingHome {

    public static final String MESSAGE_DUPLICATE_ENTITY = "Duplicate entities detected.";

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBakingHome}.
     */
    @JsonCreator
    public JsonSerializableBakingHome(@JsonProperty("orders") List<JsonAdaptedOrder> orders) {
        this.orders.addAll(orders);
    }

    /**
     * Converts a given {@code ReadOnlyBakingHome} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBakingHome}.
     */
    public JsonSerializableBakingHome(ReadOnlyBakingHome source) {
        orders.addAll(source.getOrderList().stream().map(JsonAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BakingHome toModelType() throws IllegalValueException {
        BakingHome bakingHome = new BakingHome();
        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            Order order = jsonAdaptedOrder.toModelType();
            if (bakingHome.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            }
            bakingHome.addOrder(order);
        }
        return addressBook;
    }

}
