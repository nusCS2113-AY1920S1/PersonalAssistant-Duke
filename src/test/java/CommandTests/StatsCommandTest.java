package CommandTests;

import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;
import rims.command.StatsCommand;
import rims.exception.*;
import rims.resource.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
public class StatsCommandTest {
    private StatsCommand commandUnderTest;
    private static Ui ui;
    private static Storage storage;
    private static ResourceList listUnderTest;
    private static Resource ResourceUnderTest;


    /**
     * This before all method creates list, storage, reservations for later tests.
     * 
     * @throws RimsException
     * @throws ParseException
     * @throws FileNotFoundException
     * @throws IOException
     */
    @BeforeAll
    private static void init() throws RimsException, ParseException, FileNotFoundException, IOException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);
        ResourceUnderTest = new Item(1, "testitem");
        listUnderTest.add(ResourceUnderTest);
        storage = new Storage("unitTestData/resources.txt","unitTestData/reserves.txt");
    }

    @Test
    public void DateIntervalTooBigTest() throws RimsException, ParseException {

        commandUnderTest = new StatsCommand("10/10/2021 1000", "11/11/2021 1000");
        Exception e = assertThrows(RimsException.class, () -> {
            commandUnderTest.execute(ui, storage, listUnderTest);
        });

        assertEquals("The date interval is too large (more than 14 days)", e.getMessage());
        System.out.print("Test: Trying to return a non-existing reservation\nStatus: Passed\n");
    }

}