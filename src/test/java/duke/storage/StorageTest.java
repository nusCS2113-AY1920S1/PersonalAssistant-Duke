package duke.storage;

import duke.commons.DukeException;
import duke.model.order.Order;
import duke.parser.TimeParser;
import org.junit.jupiter.api.Test;

public class StorageTest {

    @Test
    private void serializeAndDeserialize_bakingListWithOnlyOrderList_success() throws DukeException {
        Order order = new Order("jj", "1234", TimeParser.convertStringToDate("10/10/1999 18:00"));
        BakingList bakingList = new BakingList();
        bakingList.getOrderList().add(order);
        Storage storage = new Storage("baking.json");
        storage.serialize(bakingList);
        BakingList deserializedBakingList = storage.deserialize();
        assertEquals("jj", deserializedBakingList.getOrderList().get(0).getCustomerName());
    }
}
