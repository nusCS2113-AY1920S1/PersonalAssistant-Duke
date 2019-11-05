import rims.core.*;
import rims.command.*;
import rims.exception.*;
import rims.resource.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class ReserveCommandTest {
    private ReserveCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;

    @BeforeAll
    private static void init() throws RimsException { 
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
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
        System.out.print("Test 1: passed");
    }
}