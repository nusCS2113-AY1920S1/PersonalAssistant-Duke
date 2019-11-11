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
public class DeleteCommandTest {
    private DeleteCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;
    private static Resource resource_1;
    private static Resource resource_2;
    private static Resource resource_3;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        storage = new Storage("unitTestDate/resources.txt", "unitTestDate/reserves.txt");
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
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
     * This test tests for exception handling when user tries to delete a
     * non-existing item.
     */
    @Test
    public void DeleteNonExistentItemTest() throws RimsException {
        commandUnderTest = new DeleteCommand("ball", "item");
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });

        assertEquals("This resource does not exist in your inventory!", e.getMessage());
        System.out.print("Test: Deleting a non-existing item\nStatus: Passed\n");
    }

    /**
     * This test tests for exception handling when user tries to delete a
     * non-existing room.
     */
    @Test
    public void DeleteNonExistentRoomTest() throws RimsException {
        commandUnderTest = new DeleteCommand("ball", "room");
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });

        assertEquals("This resource does not exist in your inventory!", e.getMessage());
        System.out.print("Test: Deleting a non-existing room\nStatus: Passed\n");
    }

    /**
     * This test test for a valid room deletion.
     */
    @Test
    public void validDeleteRoomTest() throws RimsException {
    commandUnderTest = new DeleteCommand("testroom","room");
    commandUnderTest.execute(ui,storage,listUnderTest);
    System.out.print("Test: valid room deletion\nStatus: Passed\n");
    }
}
