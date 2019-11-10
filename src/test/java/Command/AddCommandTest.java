import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import rims.command.*;
import rims.core.*;
import rims.exception.RimsException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

//@@author hin1
/**
 * Test class that tests the addition of a new resource
 * to ResourceList.
 */
public class AddCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    private Ui tempUi;
    private Storage tempStorage;
    private ResourceList tempResources;

    private AddCommand commandUnderTest;

    @TempDir
    private File tempDir;

    @BeforeAll
    public void setStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @BeforeEach
    public void init() throws RimsException {
        File testResourceFile = new File(tempDir.getName() + "tempResources.txt");
        File testReservationsFile = new File(tempDir.getName() + "tempReservations.txt");

        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());
        commandUnderTest = new AddCommand("seminar room 1");
    }

    @Test
    public void testAddCommand() throws RimsException {
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }
}
