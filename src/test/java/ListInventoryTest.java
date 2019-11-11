import command.ListInventoryCommand;
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


public class ListInventoryTest {

    // Test for correct parameter values after instantiating a new Item in inventory
    @Test
    void listInventoryTest() throws DukeException {

        Item item1 = new Item("SR1", "Chairs", 44); //3
        //Item item2 = new Item("SR1", "Tables", 10);
        //Item item3 = new Item("SR1", "Markers", 9);
        Item item4 = new Item("SR2", "Tables", 10); //4
        //Item item5 = new Item("SR2", "Projector", 1);
        Item item6 = new Item("MR1", "Tables", 3); //2
        Item item7 = new Item("Hall", "Chairs", 70); //1
        //Item item8 = new Item("SR1", "Whiteboards", 8);

        Inventory inventory = new Inventory();
        inventory.add(item1);
        //inventory.add(item2);
        //inventory.add(item3);
        inventory.add(item4);
        //inventory.add(item5);
        inventory.add(item6);
        inventory.add(item7);
        // inventory.add(item8);

        InventorySorter inventorySorter = new InventorySorter(inventory);
        ArrayList sortedInventory = inventorySorter.getSortedInvByRoom();


        assertEquals("1. Hall | Chairs | 70" + "\n"
                        + "2. MR1 | Tables | 3" + "\n"
                        + "3. SR1 | Chairs | 44" + "\n"
                        + "4. SR2 | Tables | 10",
                "1. " + item7.toString() + "\n" + "2. " + item6.toString()
                        + "3. " + item1.toString() + "\n" + "4. " + item4.toString());
    }


}
