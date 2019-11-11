package CommandTests;

import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;
import rims.command.ReserveCommand;
import rims.exception.*;
import rims.resource.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class ReserveCommandTest {
    private ReserveCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;
    private static Resource ResourceUnderTest;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        storage = new Storage("unitTestData/resources.txt","unitTestData/reserves.txt");
    }

    /**
     * This test creates an empty resource list, and tries to reserve an item with 1
     * qty, which should invoke a RimsException of item not found exception.
     */
    @Test
    public void IndexOutOfBoundExceptionTest() throws RimsException {

        commandUnderTest = new ReserveCommand("item", 1, "11/11/2020 1800", "12/11/2020 1800", 1);
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });

        assertEquals("We don't have this resource currently available in our inventory!", e.getMessage());
        System.out.print("Test: Reserving a non-existing item\nStatus: Passed\n");
    }

    /**
     * This test creates a test list with one item. It tries to reserve an item with
     * a return date before the date of borrowing.
     */
    @Test
    public void InvalidReturnDateTest() throws ParseException, RimsException {
        ResourceUnderTest = new Item(1, "TestObject");
        listUnderTest.add(ResourceUnderTest);
        // Date from is later than date till
        commandUnderTest = new ReserveCommand("TestObject", 1, "13/11/2020 1800", "12/11/2020 1800", 1);

        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });
        assertEquals("Your date of return must be after your date of borrowing!", e.getMessage());
        System.out.print("Test: A return date before borrow date\nStatus: Passed\n");
        listUnderTest.deleteResourceByName("TestObject");
    }

    /**
     * This test creates a test list with one item. It tries to reserve an item with
     * a return date and a date of borrowing that are in the past.
     * 
     * @throws ParseException
     * @throws RimsException
     */
    @Test
    public void PastDateTest() throws ParseException, RimsException {
        ResourceUnderTest = new Item(1, "TestObject");
        listUnderTest.add(ResourceUnderTest);
        // Date from is later than date till
        commandUnderTest = new ReserveCommand("TestObject", 1, "10/11/2000 1800", "12/11/2000 1800", 1);

        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });
        assertEquals("Your date of return must be a date in the future!", e.getMessage());
        System.out.print("Test: A pair of dates in the past\nStatus: Passed\n");
        listUnderTest.deleteResourceByName("TestObject");
    }

    /**
     * This test creates a reservation with a negative quantity.
     */
    @Test
    public void InvalidQuantityTest() throws ParseException, RimsException {
        ResourceUnderTest = new Item(1, "TestObject");
        listUnderTest.add(ResourceUnderTest);
        // Date from is later than date till
        commandUnderTest = new ReserveCommand("TestObject", -1, "10/11/2020 1800", "12/11/2020 1800", 1);
        
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });
        assertEquals("Reservation is not made because the user has entered 0 or a negative quantity!", e.getMessage());
        System.out.print("Test: A negative quantity is parsed\nStatus: Passed\n");
        listUnderTest.deleteResourceByName("TestObject");
    }

    /**
     * This test attempts to reserve an item with insufficient quantity.
     */
    @Test
    public void InsufficientQuantityTest() throws ParseException, RimsException {
        ResourceUnderTest = new Item(1, "TestObject");
        listUnderTest.add(ResourceUnderTest);
        commandUnderTest = new ReserveCommand("TestObject", listUnderTest.getAvailableNumberOfResource("TestObject")+1, "10/11/2022 1800", "12/11/2022 1800", 1);

        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });
        assertEquals("We don't have that many of this resource currently available!", e.getMessage());
        System.out.print("Test: Resource list has insufficient quantity for requested resource\nStatus: Passed\n");
        listUnderTest.deleteResourceByName("TestObject");
    }

    /**
     * A valid input is used here to test for success cases.
     */
    @Test
    public void validResultTest() throws RimsException, ParseException, IOException {
        ResourceUnderTest = new Item(1, "TestObject");
        listUnderTest.add(ResourceUnderTest);
        commandUnderTest = new ReserveCommand("TestObject", 1, "10/11/2022 1800", "12/11/2022 1800", 1);
        commandUnderTest.execute(ui, storage, listUnderTest);
        System.out.print("Test: Valid input test\nStatus: Passed\n");
        listUnderTest.deleteResourceByName("TestObject");
    }
}