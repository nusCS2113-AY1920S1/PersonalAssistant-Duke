import inventory.Inventory;
import command.AddInventoryCommand;
import exception.DukeException;
import org.junit.jupiter.api.Test;
import storage.Constants;
import inventory.InventorySorter;
import inventory.Item;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author wyinnnn

public class AddInventoryTest1 {
    // Test for correct parameter values after instantiating a new Item in inventory
    @Test
    void addInventoryTest1() throws DukeException {

        Item item1 = new Item("SR1", "Chairs", 44);
        ArrayList inventory = new Inventory();
        inventory.add(item1);

        assertEquals("SR1 | Chairs | 44", item1.toString());
        assertEquals("SR1", item1.getRoomcode());
        assertEquals("Chairs", item1.getName());
        assertEquals(44, item1.getQuantity());
    }


}
