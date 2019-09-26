package duke.storage;

import duke.commons.DukeException;
import duke.entities.Order;
import duke.parser.TimeParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {

    @Test
    private void serializeAndDeserialize_bakingListWithOnlyOrderList_success() throws DukeException {
        Order order = new Order("jj", "1234", TimeParser.convertStringToDate("10/10/1999 18:00"));
        BakingList bakingList = new BakingList();
        bakingList.getOrderList().add(order);
        Storage storage = new Storage("baking.json");
        storage.serialize(bakingList);
        BakingList deserilizedBakingList = storage.deserialize();
        assertEquals("jj", deserilizedBakingList.getOrderList().get(0).getCustomerName());
    }
}
