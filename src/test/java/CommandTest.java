import duke.DukeCore;
import duke.exception.DukeFatalException;
import duke.data.PatientMap;
import duke.data.TaskList;
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

    /**
     * Create data directory if necessary and use a test task file to create test DukeCore, with output directed to
     * testOut.
     */
    @BeforeAll
    public static void setupCore() {
        File dataDir = new File("data");
        if (!dataDir.exists() && !dataDir.mkdir()) {
            fail("Could not create data directory!");
        }

        try {
            core = new DukeCore();
            core.patientMap = new PatientMap();
            core.storage.writeJsonFile(core.patientMap.getPatientHashMap());
        } catch (DukeFatalException excp) {
            fail("Could not setup storage for testing!");
        }
    }

    /**
     * Reset taskList and testOut, and flush the testPrint stream after each test is done with them.
     */
    @AfterEach
    public void clearTaskList() {
        core.taskList = new TaskList();
        testPrint.flush();
        testOut.reset();
    }

    /**
     * Deletes testing data after test is completed.
     */
    @AfterAll
    public static void clearTestData() {
        File testData = new File("data" + File.separator + "test.tsv");
        if (!testData.delete()) {
            fail("Unable to delete test data file!");
        }
    }
}
