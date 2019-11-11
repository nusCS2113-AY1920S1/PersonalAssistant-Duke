import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import rims.command.*;
import rims.core.*;
import rims.exception.RimsException;
import rims.resource.Resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author hin1
/**
 * Test class that tests the addition of a new resource
 * to ResourceList.
 */
public class AddCommandTest {

    private Ui tempUi;
    private Storage tempStorage;
    private ResourceList tempResources;

    private AddCommand commandUnderTest;

    private File testResourceFile;
    private File testReservationsFile;

    @BeforeEach
    public void init() throws RimsException {
        this.testResourceFile = new File("tempAddResources.txt");
        this.testReservationsFile = new File("tempAddReservations.txt");
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());
    }

    @AfterEach
    public void deleteTempFiles() {
        testResourceFile.delete();
        testReservationsFile.delete();
    }

    @Test
    public void duplicateRoomTest() throws RimsException {
        commandUnderTest = new AddCommand("seminar room 1");
        commandUnderTest.execute(tempUi, tempStorage, tempResources);
        commandUnderTest = new AddCommand("seminar room 1");
        assertThrows(RimsException.class, () -> commandUnderTest.execute(tempUi, tempStorage, tempResources));
        System.out.println("RimsException thrown");
    }

    @Test
    public void validAddRoomTest() throws RimsException {
        commandUnderTest = new AddCommand("seminar room 2");
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }

    @Test
    public void validAddItemTest() throws RimsException {
        commandUnderTest = new AddCommand("ball", 1);
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }

    @Test
    public void validAddItemsTest() throws RimsException {
        commandUnderTest = new AddCommand("ball", 50);
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }


}
