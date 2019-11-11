package CommandTests;

import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;
import rims.command.ReturnCommand;
import rims.exception.*;
import rims.resource.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class ReturnCommandTest {
    private ReturnCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;
    private static Resource ResourceUnderTest;
    private static Reservation ReservationUnderTest;

    /**
     * This before all method creates list, storage, reservations for later tests.
     * 
     * @throws RimsException
     * @throws ParseException
     * @throws FileNotFoundException
     * @throws IOException
     */
    @BeforeEach
    private void init() throws RimsException, ParseException, FileNotFoundException, IOException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        ResourceUnderTest = new Item(1, "testitem");
        ReservationUnderTest = new Reservation(1,1,1,"10/10/2021 1000","11/10/2021 1000");
        Date datefrom = ReservationUnderTest.stringToDate("10/10/2021 1000");
        Date datetill = ReservationUnderTest.stringToDate("11/10/2021 1000");
        ResourceUnderTest.book(1, 1, datefrom, datetill);

        listUnderTest.add(ResourceUnderTest);

        storage = new Storage("unitTestData/resources.txt", "unitTestData/reserves.txt");
    }

    /**
     * This test creates an empty resource list, and tries to reserve an item with 1
     * qty, which should invoke a RimsException of item not found exception.
     */
    @Test
    public void InvalidReservationIdTest() throws RimsException {
        ArrayList<Integer> ResourceIds = new ArrayList<Integer>();
        ArrayList<Integer> ReservationIds = new ArrayList<Integer>();

        ResourceIds.add(1);
        ReservationIds.add(2);

        commandUnderTest = new ReturnCommand(1, ResourceIds, ReservationIds);
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });

        assertEquals("Reservation not found for given reservation ID!", e.getMessage());
        System.out.print("Test: Trying to return a non-existing reservation\nStatus: Passed\n");
    }

    /**
     * This test aims to test for the execption handling when a user id of an user
     * who has not made any reservation is parsed.
     */
    @Test
    public void validInputTest() throws RimsException, IOException {
        ArrayList<Integer> ResourceIds = new ArrayList<Integer>();
        ArrayList<Integer> ReservationIds = new ArrayList<Integer>();
        ResourceIds.add(1);
        ReservationIds.add(1);
        commandUnderTest = new ReturnCommand(1, ResourceIds, ReservationIds);
        commandUnderTest.execute(ui, storage, listUnderTest);
        System.out.print("Test: Trying to return a non-existing reservation\nStatus: Passed\n");
    }
}