package CommandTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import rims.command.*;
import rims.core.*;
import rims.exception.RimsException;
import rims.resource.Item;
import rims.resource.Resource;
import rims.resource.Room;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author hin1
/**
 * Test class that tests the addition of a new resource
 * to ResourceList.
 */
public class AddCommandTest {
    private AddCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;
    private static Resource resource_1;
    private static Resource resource_2;
    private static Resource resource_3;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        storage = new Storage("unitTestData/resources.txt","unitTestData/reserves.txt");
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui,emptyList);
        resource_1 = new Item(1, "testitem");
        resource_2 = new Item(2, "testitem");
        resource_3 = new Room(3, "testroom");

        listUnderTest.add(resource_1);
        listUnderTest.add(resource_2);
        listUnderTest.add(resource_3);
    }

    @AfterEach
    public void deleteTempFiles() {
        ;   
    }

    /**
     * This test tests for the exception handling when user tries to add a duplicated room.
     */
    @Test
    public void duplicateRoomTest() throws RimsException {
        commandUnderTest = new AddCommand("seminar room 1");
        commandUnderTest.execute(ui, storage, listUnderTest);
        commandUnderTest = new AddCommand("seminar room 1");
        Exception e = assertThrows(RimsException.class, () -> commandUnderTest.execute(ui, storage, listUnderTest));

        assertEquals("A room with the same name already exists in your inventory!", e.getMessage());
        System.out.print("Test: Trying to add a duplicated room\nStatus: Passed\n");
    }

    /**
     * This test tests for valid input to add a room.
     */
    @Test
    public void validAddRoomTest() throws RimsException {
        commandUnderTest = new AddCommand("seminar room 2");
        commandUnderTest.execute(ui,storage,listUnderTest);
        System.out.print("Test: Valid add input test\nStatus: Passed\n");
    }

    /**
     * This test tests for valid input to add an item.
     */
    @Test
    public void validAddItemTest() throws RimsException {
        commandUnderTest = new AddCommand("ball", 1);
        commandUnderTest.execute(ui,storage,listUnderTest);
        System.out.print("Test: Valid add input test\nStatus: Passed\n");
    }
}
