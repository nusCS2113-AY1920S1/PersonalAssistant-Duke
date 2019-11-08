import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import rims.command.*;
import rims.core.*;

import java.io.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import rims.exception.RimsException;

import java.io.File;
import java.text.ParseException;

/**
 * Check if each modifiable command can run undo properly,
 * and checks for every command undone, they output the proper arguments.
 *
 * Current list of supported commands:
 * Add
 * Delete
 * Loan
 * Return
 * Reserve
 */
public class UndoCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    private UndoCommand commandUnderTest;
    private Command commandToUndo;
    private Ui tempUi;
    private ResourceList tempResources;
    private Storage storageUnderTest;

    @TempDir
    static File tempDir;

    @BeforeAll
    public void init() throws IOException, ParseException, RimsException {

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        File testResourceFile = new File(tempDir.getName() + "tempResources.txt");
        File testReservationsFile = new File(tempDir.getName() + "tempReservations.txt");

        this.tempUi = new Ui();
        this.storageUnderTest = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,storageUnderTest.getResources());

        this.commandToUndo = new AddCommand("item1",1);
        this.commandUnderTest = new UndoCommand(commandToUndo);
    }

    @BeforeEach
    public void executeUndo() throws ParseException, IOException, RimsException {
        commandToUndo.execute(tempUi,storageUnderTest,tempResources);
        commandUnderTest.execute(tempUi,storageUnderTest,tempResources);
    }

    @Test
    public void previousCommandsAndArgumentsTest() {
        assertEquals("add 1 item1 (item)",this.commandUnderTest.getCommandUserInput());
    }

    @Test
    public void restoreStateTest() {
        assertEquals(storageUnderTest.getResources(),tempResources.getResources());
    }

    @AfterAll
    static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }


}

