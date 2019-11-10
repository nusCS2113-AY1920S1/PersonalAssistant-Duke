package templates;

import duke.DukeCore;
import duke.data.storage.GsonStorage;
import duke.data.PatientData;
import duke.exception.DukeException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Abstract test for testing command execution.
 */
public abstract class CommandTest {
    protected static DukeCore core;
    protected static ByteArrayOutputStream testOut = new ByteArrayOutputStream(); //stores printed output
    protected static PrintStream testPrint = new PrintStream(testOut); //System.out replacement, prints to testOut
    protected static final String testFilePath = "data" + File.separator + "test.json";

    /**
     * Create data directory if necessary and use a test task file to create test DukeCore, with output directed to
     * testOut.
     */
    @BeforeAll
    public static void setupCore() {
        try {
            core = new DukeCore();
            core.patientData = new PatientData();
            core.storage = new GsonStorage(testFilePath);
            core.writeJsonFile();
        } catch (DukeException excp) {
            fail("Could not setup storage for testing!");
        }
    }

    /**
     * Reset taskList and testOut, and flush the testPrint stream after each test is done with them.
     */
    @AfterEach
    public void clearPatientMap() {
        core.patientData = new PatientData();
        testPrint.flush();
        testOut.reset();
    }

    /**
     * Deletes testing data after test is completed.
     */
    @AfterAll
    public static void clearTestData() {
        File testData = new File(testFilePath);
        if (!testData.delete()) {
            fail("Unable to delete test data file!");
        }
    }
}