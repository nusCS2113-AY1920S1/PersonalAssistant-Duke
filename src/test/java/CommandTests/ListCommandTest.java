package CommandTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import rims.command.ListCommand;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

import java.text.ParseException;
import java.util.*;
import rims.exception.RimsException;
import rims.resource.*;


public class ListCommandTest {
    private ListCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;

    @BeforeAll
    private static void init() throws RimsException, ParseException {
        ui = new Ui();
        ArrayList<Resource> newList = new ArrayList<Resource>();
        ReservationList reserveList = new ReservationList();
        Reservation itemReservation = new Reservation(1, 1, 5, "20/12/2019 0900", "21/12/2019 1000");
        reserveList.add(itemReservation);
        Item newItem = new Item(1, "ball", reserveList);
        Reservation roomReservation = new Reservation(2, 2, 5, "20/12/2019 0800", "20/12/2019 0830");
        reserveList.add(roomReservation);
        Room newRoom = new Room(2, "mpsh", reserveList);
        newList.add(newItem);
        newList.add(newRoom);
        listUnderTest = new ResourceList(ui, newList);
    }

    /**
     * This test creates a resource list, and tries to list resources by item ,which
     * should not invoke a RimsException.
     * 
     * @throws RimsException
     */
    @Test
    void PrintByItemTest() throws RimsException {
        commandUnderTest = new ListCommand("item", "ball");
        commandUnderTest.execute(ui, storage, listUnderTest);
        System.out.print("Test: Valid input test (item) \nStatus: passed");
    }
    /**
     * This test creates a resource list, and tries to list resources by room
     * ,which should not invoke a RimsException.
     * 
     * @throws RimsException
     */
    @Test
    void PrintByRoomTest() throws RimsException {
        commandUnderTest = new ListCommand("room", "mpsh");
        commandUnderTest.execute(ui, storage, listUnderTest);
        System.out.print("Test: Valid input test (room) \nStatus: passed");
    }
}
